package com.itlong.whatsmars.common.util;

import com.itlong.whatsmars.common.pojo.Result;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * qing
 * JSON字符串与java object之间的互相转换
 *
 */
public class JSONConverterUtils {

	/**
	 * if not static,performance will be cost so much;but if you have self config,
     *  please get your own instance;
	 */
	private static final ObjectMapper mapper = new ObjectMapper();

	public static <T> T converter(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public static String converter(Object obj) {
		try{
			return mapper.writeValueAsString(obj);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args){
        Result result = new Result();
        result.setSuccess(true);
        result.setResponseUrl("/test.jhtml");
        result.addModel("test","test");
        String json = JSONConverterUtils.converter(result);
        System.out.println(json);
	}
}
