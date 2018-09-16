package org.hongxi.whatsmars.javase.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2017/8/21.
 */
public class ObjectMapperTest {

    @Test
    public void t() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("domain", "sina.im");
        params.put("year", 2017);
        String s = mapper.writeValueAsString(params);
        System.out.println(s);
    }
}
