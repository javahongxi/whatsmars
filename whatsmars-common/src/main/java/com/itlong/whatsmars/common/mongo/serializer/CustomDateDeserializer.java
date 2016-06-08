package com.itlong.whatsmars.common.mongo.serializer;

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

	private static final String[] pattern = { "yyyy-MM-dd HH:mm:ss" };

	@Override
	public Date deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		if (JsonToken.VALUE_STRING.equals(arg0.getCurrentToken())) {
			try {
				return DateUtils.parseDate(arg0.getText(), pattern);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
