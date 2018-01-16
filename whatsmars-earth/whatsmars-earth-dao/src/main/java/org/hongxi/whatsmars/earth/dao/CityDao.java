package org.hongxi.whatsmars.earth.dao;

import org.hongxi.whatsmars.earth.domain.pojo.City;

import java.util.List;

/**
 * Created by shenhongxi on 2018/1/16.
 */
public interface CityDao {

    List<City> getByPid(String pid);
}
