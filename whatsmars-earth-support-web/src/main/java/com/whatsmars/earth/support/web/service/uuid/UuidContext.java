package com.whatsmars.earth.support.web.service.uuid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by shenhongxi on 2016/8/12.
 */
public class UuidContext {

    private static final Logger log = LoggerFactory.getLogger(UuidContext.class);

    // 缓存DB中的截止数
    public static ConcurrentMap<String, Long> endCache = new ConcurrentHashMap<String,Long>();
    // 缓存当前增加到的数值
    public static ConcurrentMap<String, Long> nowCache = new ConcurrentHashMap<String,Long>();
    // 缓存共享对象
    public static ConcurrentMap<String, UuidModel> uuidCache = new ConcurrentHashMap<String, UuidModel>();
    // 缓存配置
    public static ConcurrentMap<String, Config> configCache = new ConcurrentHashMap<String, Config>();

    static UuidDao uuidDao;

    public static void init(){
        loadConfig();

        uuidDao = SpringHelper.getBean("uuidDao", UuidDao.class);
        List<UuidModel> list = uuidDao.getAll();
        for (UuidModel um : list) {
            Config cm = getConfig(um.getName());
            updateUuid(um, cm.getLength());
            loadMemory(um);
        }
    }

    /**
     * 根据名称更新号段 直至成功
     * @param um
     * @return
     */
    public static UuidModel updateUuid(UuidModel um, int length){
        boolean updated = false;
        do{
            UuidModel _um = uuidDao.findByName(um.getName());
            int cacheSize = 1000;
            Config config = getConfig(um.getName());
            if (config != null) {
                cacheSize = config.getCacheSize();
            }
            // 判断是否需要重置 条件为：1.配置的重置数<新段的截止数 则需要重置
            // 2.新段的截止数大于需要获取的位数 则需要重置
            long resetNum = config.getResetNum();
            // 取得新段的截止数
            long newEnd = _um.getEnd() + cacheSize;
            um.setOldEnd(_um.getEnd());
            um.setOldStart(_um.getStart());
            if ((resetNum < newEnd) || (String.valueOf(newEnd).length() > length)) {
                // 需要重置为0开始段
                um.setStart(0);
                um.setEnd(cacheSize);
            } else {
                // 取新段
                um.setStart(_um.getEnd());
                um.setEnd(_um.getEnd() + cacheSize);
            }

            // 最终的更新成功保证了多实例部署时，各实例持有的号段不同
            updated = uuidDao.update(um);
        } while (!updated);

        return um;
    }

    /**
     * 载入内存
     * @param um
     */
    public static void loadMemory(UuidModel um){
        endCache.put(um.getName(), um.getEnd());
        nowCache.put(um.getName(), um.getStart());
        uuidCache.put(um.getName(), um);
    }

    public static Config getConfig(String name) {
        Config config = configCache.get(name);
        if (config == null) {
            config = configCache.get("default");
        }
        return config;
    }

    private static void loadConfig(){
        try {
            String businesses = AppConfig.getValue("businesses");
            for(String biz : businesses.split(",")){
                String bizConfig = AppConfig.getValue(biz);
                String[] configs = bizConfig.split(",");
                int cacheSize = Integer.valueOf(configs[0]);
                int length = Integer.valueOf(configs[1]);
                String fillChar = configs[2];
                long warnNum = Long.valueOf(configs[3]);
                long resetNum = Long.valueOf(configs[4]);
                int tactics = Integer.valueOf(configs[5]);
                Config config = new Config();
                config.setCacheSize(cacheSize);
                config.setFillChar(fillChar);
                config.setLength(length);
                config.setPrefix("");
                config.setSuffix("");
                config.setStrategy(tactics);
                config.setResetNum(resetNum);
                config.setWarnNum(warnNum);
                config.setName(biz);
                configCache.put(biz, config);
            }
            log.info("load config success");
        } catch (Exception e) {
            log.error("load config error", e);
        }

    }
}

