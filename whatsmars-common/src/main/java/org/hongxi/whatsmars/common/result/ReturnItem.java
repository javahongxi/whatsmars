package org.hongxi.whatsmars.common.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReturnItem<T> {

	/* 请求状态 */
	private int status;
	/* 查询结果 */
	private T item;

	@JsonIgnore
	private String[] ignoreFields = new String[]{};

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}

    public String[] getIgnoreFields() {
        return ignoreFields;
    }

    public void setIgnoreFields(String[] ignoreFields) {
        this.ignoreFields = ignoreFields;
    }

    public ReturnItem() {
		super();
	}

	public ReturnItem(int status, T item) {
		super();
		this.status = status;
		this.item = item;
	}

    public ReturnItem(int status, T item, String[] ignoreFields) {
        this.status = status;
        this.item = item;
        this.ignoreFields = ignoreFields;
    }
}
