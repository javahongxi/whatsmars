package org.hongxi.whatsmars.earth.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.hongxi.whatsmars.earth.dao.CityDao;
import org.hongxi.whatsmars.earth.domain.pojo.City;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by shenhongxi on 2018/1/16.
 */
public class CityService {

    private CityDao cityDao;

    private static Cache<String,List<City>> cache = CacheBuilder.newBuilder().maximumSize(2048).expireAfterAccess(15, TimeUnit.MINUTES).build();

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public List<City> getByPid(final String pid) {
        try {
            //cache.get方法首先从Cache中查询key是否存在，如果存在则返回对应的value。
            //如果不存在，则调用Callable.call方法。并把call方法返回的结果保存在cache中。
            return cache.get(pid, new Callable<List<City>>() {
                @Override
                public List<City> call() throws Exception {
                    return load(pid);
                }
            });
        }catch (Exception e) {
            //如果数据库不存在此值，将会返回一个空的集合
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * 如果后台DAO返回结果为null，需要抛出异常。因为guava cache中不能存储null值。
     *
     * Callable的返回值将会被保存在cache中，然后返回给调用方法。
     * 如果抛出异常，异常将会通过cache.get方法抛出。
     * @param pid
     * @return
     * @throws Exception
     */
    protected List<City> load(final String pid) throws Exception{
        List<City> result = this.cityDao.getByPid(pid);
        if(result == null) {
            throw new NullPointerException("pid not existed");
        }
        return result;
    }
}
