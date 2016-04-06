package com.whatsmars.mars001.dao;

import com.whatsmars.mars001.domain.pojo.OrganizationDO;
import com.whatsmars.mars001.domain.query.OrganizationQuery;
import com.whatsmars.mars001.domain.query.QueryResult;

/**
 * Created by shenhongxi on 2016/4/6.
 */
public interface OrganizationDao {

    public QueryResult<OrganizationDO> query(OrganizationQuery query);
}
