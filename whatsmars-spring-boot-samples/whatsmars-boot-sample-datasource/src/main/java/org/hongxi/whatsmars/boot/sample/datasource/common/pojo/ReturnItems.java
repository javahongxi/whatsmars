package org.hongxi.whatsmars.boot.sample.datasource.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ReturnItems<T> {

	/**
	 * 请求状态
	 */
	private int status;
	/**
	 * 总条数
	 */
	private Long total;
	/**
	 * 查询结果集
	 */
	private List<T> items;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date serverDateTime = new Date();

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTotal() {
		if(total == null){
			return items != null ? items.size() : 0;
		}
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
	

	public Date getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(Date serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public ReturnItems() {
		super();
	}

	public ReturnItems(int status, long total, List<T> items) {
		super();
		this.status = status;
		this.total = total;
		this.items = items;
	}

	public ReturnItems(List<T> items) {
		super();
		this.items = items;
	}

}
