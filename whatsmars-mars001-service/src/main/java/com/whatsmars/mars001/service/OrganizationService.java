package com.whatsmars.mars001.service;

import com.whatsmars.common.pojo.Result;
import com.whatsmars.mars001.domain.query.OrganizationQuery;

/**
 * Created by shenhongxi on 15/4/13.
 */
public interface OrganizationService {

    public Result list(OrganizationQuery query);

}
