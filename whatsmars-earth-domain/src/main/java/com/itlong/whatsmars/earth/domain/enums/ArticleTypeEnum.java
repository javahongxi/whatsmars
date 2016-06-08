package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by chenguang on 2015/6/29 0029.
 */
public enum ArticleTypeEnum {

    NOTICE(1, "最新公告"),
    MEDIA(2, "媒体报道");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    ArticleTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static ArticleTypeEnum value(int code){
        for(ArticleTypeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
