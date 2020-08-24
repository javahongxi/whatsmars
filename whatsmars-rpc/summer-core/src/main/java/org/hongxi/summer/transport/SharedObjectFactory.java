package org.hongxi.summer.transport;

/**
 * Created by shenhongxi on 2020/7/28.
 */
public interface SharedObjectFactory<T> {

    /**
     * 创建对象
     *
     * @return
     */
    T makeObject();

    /**
     * 重建对象
     *
     * @param obj
     * @param async
     * @return
     */
    boolean rebuildObject(T obj, boolean async);
}
