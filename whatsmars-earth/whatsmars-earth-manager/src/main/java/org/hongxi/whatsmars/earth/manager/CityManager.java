package org.hongxi.whatsmars.earth.manager;

import org.hongxi.whatsmars.earth.domain.pojo.City;

import java.util.List;

/**
 * Created by shenhongxi on 2018/11/28.
 */
public interface CityManager {

    List<City> getByPid(String pid);
}
