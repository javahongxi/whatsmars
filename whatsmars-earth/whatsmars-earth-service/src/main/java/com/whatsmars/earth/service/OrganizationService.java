package com.whatsmars.earth.service;

import org.hongxi.whatsmars.common.pojo.Result;
import com.whatsmars.earth.domain.query.OrganizationQuery;

/**
 * Created by shenhongxi on 15/4/13.
 */
public interface OrganizationService {

    public Result list(OrganizationQuery query);

}
