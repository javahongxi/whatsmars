package org.hongxi.whatsmars.earth.dao;

import org.hongxi.whatsmars.earth.domain.pojo.Organization;
import org.hongxi.whatsmars.earth.domain.query.OrganizationQuery;
import org.hongxi.whatsmars.earth.domain.query.QueryResult;

/**
 * Created by shenhongxi on 2016/4/6.
 */
public interface OrganizationDao {

    public QueryResult<Organization> query(OrganizationQuery query);
}
