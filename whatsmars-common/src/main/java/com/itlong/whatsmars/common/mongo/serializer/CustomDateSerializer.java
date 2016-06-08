package com.itlong.whatsmars.common.mongo.serializer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.util.Date;


public class CustomDateSerializer extends JsonSerializer<Date> {  
  private static final String pattern = "yyyy-MM-dd HH:mm:ss";

  @Override  
  public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {  
    jgen.writeString(DateFormatUtils.format(value, pattern));
  }  
}  