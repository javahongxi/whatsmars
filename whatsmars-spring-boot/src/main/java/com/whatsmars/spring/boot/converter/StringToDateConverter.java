package com.whatsmars.spring.boot.converter;

import com.whatsmars.spring.boot.common.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by zhaozhong on 2016/9/6.
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {

    private Pattern dateTimePattern = Pattern.compile(DateUtils.DATETIME_FORMAT_REGEX);
    private Pattern shortDatePattern = Pattern.compile(DateUtils.SHORTDATE_FORMAT_REGEX);
    private Pattern shortDate2Pattern = Pattern.compile(DateUtils.SHORTDATE_FORMAT2_REGEX);
    private Pattern datePattern = Pattern.compile(DateUtils.DATE_FORMAT_REGEX);
    private Pattern date2Pattern = Pattern.compile(DateUtils.DATE_FORMAT2_REGEX);

    @Override
    public Date convert(String source) {
        if (!StringUtils.isEmpty(source)) {
            if(shortDatePattern.matcher(source).matches()) {
                return DateUtils.getDateFormat(source, DateUtils.DATE_SHORT_FORMAT);
            }else if(datePattern.matcher(source).matches()) {
                return DateUtils.getDateFormat(source, DateUtils.DATEFORMAT);
            }else if(dateTimePattern.matcher(source).matches()) {
                return DateUtils.getDateFormat(source, DateUtils.DATETIMEFORMAT);
            }else if(shortDate2Pattern.matcher(source).matches()) {
                return DateUtils.getDateFormat(source, DateUtils.DATE_SHORT_FORMAT2);
            }else if(date2Pattern.matcher(source).matches()) {
                return DateUtils.getDateFormat(source, DateUtils.DATEFORMAT2);
            }
        }
        return null;
    }
}
