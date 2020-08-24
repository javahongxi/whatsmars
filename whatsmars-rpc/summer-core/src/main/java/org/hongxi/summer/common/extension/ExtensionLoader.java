package org.hongxi.summer.common.extension;

import org.apache.commons.lang3.StringUtils;
import org.hongxi.summer.common.SummerConstants;
import org.hongxi.summer.exception.SummerFrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by shenhongxi on 2020/6/25.
 */
public class ExtensionLoader<T> {
    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

    private static ConcurrentMap<Class<?>, ExtensionLoader<?>> extensionLoaders = new ConcurrentHashMap<>();

    private ConcurrentMap<String, Class<T>> extensionClasses;
    private ConcurrentMap<String, T> singletonInstances;

    private Class<T> type;
    private volatile boolean init;
    
    private static final String SERVICES_DIRECTORY = "META-INF/services/";
    private ClassLoader classLoader;

    private ExtensionLoader(Class<T> type) {
        this(type, Thread.currentThread().getContextClassLoader());
    }

    private ExtensionLoader(Class<T> type, ClassLoader classLoader) {
        this.type = type;
        this.classLoader = classLoader;
    }

    public T getExtension(String name) {
        if (name == null) return null;

        checkInit();

        try {
            Spi spi = type.getAnnotation(Spi.class);
            if (spi.scope() == Scope.SINGLETON) {
                return getSingletonInstance(name);
            }

            Class<T> clazz = extensionClasses.get(name);
            if (clazz == null) {
                return null;
            }
            return clazz.newInstance();
        } catch (Exception e) {
            throw new SummerFrameworkException(type.getName() + ": Error get extension " + name, e);
        }
    }

    private T getSingletonInstance(String name) throws IllegalAccessException, InstantiationException {
        T obj = singletonInstances.get(name);

        if (obj != null) {
            return obj;
        }

        Class<T> clazz = extensionClasses.get(name);
        if (clazz == null) {
            return null;
        }

        synchronized (singletonInstances) {
            obj = singletonInstances.get(name);
            if (obj != null) {
                return obj;
            }
            obj = clazz.newInstance();
            singletonInstances.put(name, obj);
        }

        return obj;
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        checkInterfaceType(type);

        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type);
        if (loader == null) {
            loader = initExtensionLoader(type);
        }
        return loader;
    }

    private static <T> void checkInterfaceType(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new SummerFrameworkException(clazz.getName() + ": Extension type is not interface");
        }
        if (!clazz.isAnnotationPresent(Spi.class)) {
            throw new SummerFrameworkException(clazz.getName() + ": Extension type without @Spi annotation");
        }
    }

    private static synchronized <T> ExtensionLoader<T> initExtensionLoader(Class<T> type) {
        ExtensionLoader<T> loader = (ExtensionLoader<T>) extensionLoaders.get(type);
        if (loader == null) {
            loader = new ExtensionLoader<>(type);
            extensionLoaders.put(type, loader);
        }
        return loader;
    }

    public List<T> getExtensions() {
        return getExtensions(null);
    }

    /**
     * 有些地方需要spi的所有激活的instances，所以需要能返回一个列表的方法
     * 注意：1 SpiMeta 中的active 为true
     *      2 按照spiMeta中的sequence进行排序
     *
     * @return
     */
    public List<T> getExtensions(String key) {
        checkInit();

        if (extensionClasses.size() == 0) {
            return Collections.emptyList();
        }

        // 如果只有一个实现，直接返回
        List<T> exts = new ArrayList<T>(extensionClasses.size());

        // 多个实现，按优先级排序返回
        for (Map.Entry<String, Class<T>> entry : extensionClasses.entrySet()) {
            Activation activation = entry.getValue().getAnnotation(Activation.class);
            if (StringUtils.isBlank(key)) {
                exts.add(getExtension(entry.getKey()));
            } else if (activation != null && activation.key() != null) {
                for (String k : activation.key()) {
                    if (key.equals(k)) {
                        exts.add(getExtension(entry.getKey()));
                        break;
                    }
                }
            }
        }
        Collections.sort(exts, new ActivationComparator<T>());
        return exts;
    }

    private void checkInit() {
        if (!init) {
            loadExtensionClasses();
        }
    }

    private synchronized void loadExtensionClasses() {
        if (init) return;
        
        extensionClasses = loadExtensionClasses(SERVICES_DIRECTORY);
        singletonInstances = new ConcurrentHashMap<>();
        
        init = true;
    }

    private ConcurrentMap<String, Class<T>> loadExtensionClasses(String dir) {
        String fullName = dir + type.getName();
        List<String> classNames = new ArrayList<>();

        try {
            Enumeration<URL> urls;
            if (classLoader == null) {
                urls = ClassLoader.getSystemResources(fullName);
            } else {
                urls = classLoader.getResources(fullName);
            }

            if (urls == null || !urls.hasMoreElements()) {
                return new ConcurrentHashMap<>();
            }

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                parseUrl(type, url, classNames);
            }
        } catch (Exception e) {
            throw new SummerFrameworkException(
                    "ExtensionLoader loadExtensionClasses error, services dir: " + dir + ", type: " + type.getClass(), e);
        }

        return loadClasses(classNames);
    }

    private void parseUrl(Class<T> type, URL url, List<String> classNames) {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = url.openStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, SummerConstants.DEFAULT_CHARSET));
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                parseLine(type, url, line, ++lineNumber, classNames);
            }
        } catch (Exception e) {
            logger.error("{}: Error reading spi configuration file", type.getName(), e);
        } finally {
            try {
                if (reader != null) reader.close();
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                logger.error("{}: Error closing spi configuration file", type.getName(), e);
            }
        }
    }

    private void parseLine(Class<T> type, URL url, String line, int lineNumber, List<String> classNames) {
        int ci = line.indexOf('#');
        if (ci > 0) line = line.substring(0, ci);
        line = line.trim();

        if (line.isEmpty()) return;

        if (line.indexOf(' ') >= 0 || line.indexOf('\t') >= 0) {
            throw new SummerFrameworkException(type.getName() + ": " + url + ": " + lineNumber + ": Illegal spi configuration-file syntax");
        }

        int cp = line.codePointAt(0);
        if (!Character.isJavaIdentifierStart(cp)) {
            throw new SummerFrameworkException(type.getName() + ": " + url + ": " + lineNumber + ": Illegal spi provider-class name: " + line);
        }

//        for (int i = Character.charCount(cp); i < line.length(); i += Character.charCount(cp)) {
//            cp = line.codePointAt(i);
//            if (!Character.isJavaIdentifierStart(cp) && cp != '.') {
//                throw new SummerFrameworkException(type.getName() + ": " + url + ": " + lineNumber + ": Illegal spi provider-class name: " + line);
//            }
//        }

        if (!classNames.contains(line)) {
            classNames.add(line);
        }
    }

    private ConcurrentMap<String, Class<T>> loadClasses(List<String> classNames) {
        ConcurrentMap<String, Class<T>> classes = new ConcurrentHashMap<>();
        for (String className : classNames) {
            try {
                Class<T> clazz;
                if (classLoader == null) {
                    clazz = (Class<T>) Class.forName(className);
                } else {
                    clazz = (Class<T>) Class.forName(className, true, classLoader);
                }

                checkExtensionType(clazz);

                String spiName = getSpiName(clazz);
                if (classes.containsKey(spiName)) {
                    throw new SummerFrameworkException(clazz + ": spi name already exists: " + spiName);
                } else {
                    classes.put(spiName, clazz);
                }
            } catch (Exception e) {
                logger.error(type.getName() + ": Error load spi class", e);
            }
        }

        return classes;
    }

    private void checkExtensionType(Class<T> clazz) {
        checkClassPublic(clazz);
        checkConstructorPublic(clazz);
        checkClassInherit(clazz);
    }

    private void checkClassPublic(Class<T> clazz) {
        if (!Modifier.isPublic(clazz.getModifiers())) {
            throw new SummerFrameworkException(clazz.getName() + "is not a public class");
        }
    }

    private void checkConstructorPublic(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors == null || constructors.length == 0) {
            throw new SummerFrameworkException(clazz.getName() + "has no public no-args constructor");
        }

        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterTypes().length == 0) {
                return;
            }
        }
        throw new SummerFrameworkException(clazz.getName() + "has no public no-args constructor");
    }

    private void checkClassInherit(Class<T> clazz) {
        if (!type.isAssignableFrom(clazz)) {
            throw new SummerFrameworkException(clazz.getName() + "is not instanceof " + type.getName());
        }
    }

    public String getSpiName(Class<?> clazz) {
        SpiMeta spiMeta = clazz.getAnnotation(SpiMeta.class);
        return (spiMeta != null && !"".equals(spiMeta.name())) ? spiMeta.name() : clazz.getSimpleName();
    }
}
