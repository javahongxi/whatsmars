package org.hongxi.whatsmars.earth.dao.impl;

import org.hongxi.whatsmars.earth.dao.OrganizationDao;
import org.hongxi.whatsmars.earth.domain.pojo.Organization;
import org.hongxi.whatsmars.earth.domain.query.OrganizationQuery;
import org.hongxi.whatsmars.earth.domain.query.QueryResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by shenhongxi on 2016/4/6.
 */
public class OrganizationDaoImpl extends BaseDao implements OrganizationDao {
    @Override
    public QueryResult<Organization> query(OrganizationQuery query) {
        QueryResult<Organization> qr = new QueryResult<Organization>();
        qr.setQuery(query);

        Map<String,Object> params = query.build();
        int amount = this.countAll(params);
        qr.setAmount(amount);
        if(amount == 0) {
            qr.setResultList(Collections.EMPTY_LIST);
            return qr;
        }

        List<Organization> organizations =  this.sqlSession.selectList("OrganizationMapper.query", params);
        qr.setResultList(organizations);
        return qr;
    }

    private int countAll(Map<String,Object> params) {
        //return this.sqlSession.selectOne("OrganizationMapper.countAll",params);
        return 0;
    }
}
