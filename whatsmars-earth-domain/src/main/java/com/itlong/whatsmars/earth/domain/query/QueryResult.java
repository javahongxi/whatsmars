package com.itlong.whatsmars.earth.domain.query;

import java.io.Serializable;
import java.util.*;

/**
 * 分页查询结果
 * @param <T>
 */
public class QueryResult<T> implements Serializable {

    private Integer totalPage;//总页数
    private Integer amount;//总条数
    private Collection<T> resultList = new ArrayList<T>();//当前页数据集合
    private BaseQuery query;


    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        if (amount == null) {
            return;
        }
        if (query == null) {
            return;
        }
        int pageSize = query.getPageSize();
        int mod = amount % pageSize;
        int page = amount / pageSize;
        totalPage = (mod == 0 ? page : page + 1);
        this.amount = amount;
    }

    public Collection<T> getResultList() {
        return resultList;
    }

    public void setResultList(Collection<T> resultList) {
        this.resultList = resultList;
    }

    public BaseQuery getQuery() {
        return query;
    }

    public void setQuery(BaseQuery query) {
        this.query = query;
    }

    public Integer getNextPage() {
        Integer nextPage = query.getCurrentPage() + 1;
        return nextPage > totalPage ? -1 : nextPage;
    }

    public List<Integer> pageNumbers() {
        if(totalPage == 0) {
            return Collections.EMPTY_LIST;
        }
        List<Integer> pages = new ArrayList<Integer>();
        for(int i = 1; i<= totalPage; i++) {
            pages.add(i);
        }
        return pages;
    }

    //***************************utils*****************************//
    public Map<String, Object> propertyMap() {
        Map<String, Object> pm = new HashMap<String, Object>(5);
        pm.put("amount", this.amount);
        pm.put("totalPage", this.totalPage);
        pm.put("resultList", this.resultList);
        if (query != null) {
            pm.putAll(query.build());
        }
        return pm;
    }
}
