package org.hongxi.whatsmars.earth.manager.impl;

import org.hongxi.whatsmars.earth.domain.pojo.City;
import org.hongxi.whatsmars.earth.manager.CityManager;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shenhongxi on 2018/11/28.
 * Manager层：通用业务处理层，它有如下特征：
 * 1） 对第三方平台封装的层，预处理返回结果及转化异常信息；
 * 2） 对Service层通用能力的下沉，如缓存方案、中间件通用处理；
 * 3） 与DAO层交互，对多个DAO的组合复用。
 */
@Component
public class CityManagerImpl implements CityManager {
    @Override
    public List<City> getByPid(String pid) {
        // 加载进guava缓存
        return null;
    }
}
