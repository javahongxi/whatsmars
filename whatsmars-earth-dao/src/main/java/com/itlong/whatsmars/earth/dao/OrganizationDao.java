package com.itlong.whatsmars.earth.dao;

import com.itlong.whatsmars.earth.domain.pojo.Organization;
import com.itlong.whatsmars.earth.domain.query.OrganizationQuery;
import com.itlong.whatsmars.earth.domain.query.QueryResult;

/**
 * Created by shenhongxi on 2016/4/6.
 */
public interface OrganizationDao {

    public QueryResult<Organization> query(OrganizationQuery query);
}
