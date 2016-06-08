package com.itlong.whatsmars.earth.service;

import com.itlong.whatsmars.common.pojo.Result;
import com.itlong.whatsmars.earth.domain.query.OrganizationQuery;

/**
 * Created by shenhongxi on 15/4/13.
 */
public interface OrganizationService {

    public Result list(OrganizationQuery query);

}
