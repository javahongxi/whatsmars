package com.whatsmars.mars001.domain.permission;

import com.whatsmars.mars001.domain.enums.UserTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuguanqing on 15/4/18.
 * 系统后台权限控制
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Permission {
    public UserTypeEnum[] roles();//用户类型

    public String[] operations() default {};//用户需要具备的操作
}
