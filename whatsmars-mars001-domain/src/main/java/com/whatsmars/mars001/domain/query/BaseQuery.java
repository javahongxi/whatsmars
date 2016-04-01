package com.whatsmars.mars001.domain.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本查询
 */
public class BaseQuery implements Serializable{

	
	protected Integer pageSize = 5;//每页数据条数

    private Integer currentPage = 1;//当前页码

    private int startRow = 0; //

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize == null || pageSize <= 0){
			return;
		}
		this.pageSize = pageSize;
	}

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        if (currentPage == null || currentPage <= 0) {
            return;
        }
        this.currentPage = currentPage;
    }

    public Integer getStartRow(){
        if(pageSize == null || pageSize <0 || currentPage == null || currentPage < 0){
            return 0;
        }
        startRow = (currentPage -1) * pageSize;
        return startRow;
    }

    public Map<String, Object> build() {
        Map<String, Object> params = new HashMap<String, Object>(5);
        params.put("pageSize", this.getPageSize());
        params.put("currentPage", this.getCurrentPage());
        params.put("startRow",this.getStartRow());
        return params;
    }

}
