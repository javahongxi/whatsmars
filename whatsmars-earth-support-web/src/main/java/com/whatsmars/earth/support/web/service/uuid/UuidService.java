package com.whatsmars.earth.support.web.service.uuid;

/**
 * Created by shenhongxi on 2016/8/12.
 */
public interface UuidService {

    /**
     * 获取自增id，从1开始
     * @param name
     * @return
     */
    String nextUuid(String name);
}
