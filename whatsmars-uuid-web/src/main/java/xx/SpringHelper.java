package xx;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class SpringHelper implements ServletContextListener {
	private static ServletContext servletContext = null;
	private static final String SPRING_XML_CLASS_PATH_STR = "classpath*:spring*.xml";
	private static ApplicationContext noContainerCtxt = null;


	public static ApplicationContext getContext(){
		if(servletContext != null)
			return WebApplicationContextUtils.getWebApplicationContext(servletContext);
		
		if(noContainerCtxt != null)
			return noContainerCtxt;

		synchronized (SPRING_XML_CLASS_PATH_STR) {
			if(noContainerCtxt == null){
				noContainerCtxt = new ClassPathXmlApplicationContext(new String[]{SPRING_XML_CLASS_PATH_STR});
			}
		}
		
		return noContainerCtxt;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name, Class<T> clazz) {
		
		return (T)getContext().getBean(name, clazz);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		try {
			UuidContext.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
