package com.whatsmars.mars001.service.impl;

import com.whatsmars.common.pojo.Result;
import com.whatsmars.common.pojo.ResultCode;
import com.whatsmars.mars001.dao.OrganizationDao;
import com.whatsmars.mars001.domain.pojo.OrganizationDO;
import com.whatsmars.mars001.domain.query.OrganizationQuery;
import com.whatsmars.mars001.domain.query.QueryResult;
import com.whatsmars.mars001.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by shenhongxi on 2016/4/6.
 */
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationDao organizationDao;

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    @Override
    public Result list(OrganizationQuery query) {
        Result result = new Result();
        try {
            QueryResult<OrganizationDO> qr = this.organizationDao.query(query);
            Collection<OrganizationDO> organizations = qr.getResultList();
            result.addModel("query", query);
            result.addModel("queryResult", qr);
            result.addModel("organizations", organizations);
        } catch (Exception e) {
            //logger.error("organization query error,", e);
            result.setResultCode(ResultCode.SYSTEM_ERROR);
        }
        return result;
    }
}
