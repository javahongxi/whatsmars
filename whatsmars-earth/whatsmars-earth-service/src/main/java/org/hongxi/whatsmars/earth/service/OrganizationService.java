package org.hongxi.whatsmars.earth.service;

import org.hongxi.whatsmars.common.pojo.Result;
import org.hongxi.whatsmars.earth.domain.query.OrganizationQuery;

/**
 * Created by shenhongxi on 15/4/13.
 */
public interface OrganizationService {

    public Result list(OrganizationQuery query);

}
