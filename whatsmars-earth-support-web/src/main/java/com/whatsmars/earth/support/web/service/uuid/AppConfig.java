package com.whatsmars.earth.support.web.service.uuid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AppConfig {
	static final Properties p = new Properties();
	static{
		try{
			File fp = new File(AppConfig.class.getResource("/props/config.properties").toURI());
			p.load(new FileInputStream(fp));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("找不到config属性文件");
		}
	}
	
	public static int getInt(String key){
		return Integer.parseInt(p.getProperty(key));
	}
	
	public static String getValue(String key) throws Exception{
		return p.getProperty(key);
	}
	
	public static String trim(String s){
		return s == null ? "" : s.trim();
	}
	

	public static void loadProperty(InputStream in) throws IOException{
			p.load(in);
	}
}
