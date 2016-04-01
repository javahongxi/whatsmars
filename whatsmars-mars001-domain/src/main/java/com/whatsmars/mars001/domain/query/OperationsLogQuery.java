package com.whatsmars.mars001.domain.query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/4/29 0029.
 */
public class OperationsLogQuery extends BaseQuery {

    private Integer operation;

    private List<Integer> operations;

    private Date beginDate;

    private String queryBeginDate;

    private Date endDate;

    private String queryEndDate;

    private String name;

    private String operator;



    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public List<Integer> getOperations() {
        return operations;
    }

    public void setOperations(List<Integer> operations) {
        this.operations = operations;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getQueryBeginDate() {
        return queryBeginDate;
    }

    public void setQueryBeginDate(String queryBeginDate) {
        this.queryBeginDate = queryBeginDate;
    }

    public String getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("begin_date=");
        sb.append(this.queryBeginDate == null ? "" : this.queryBeginDate);
        sb.append("&end_date=");
        sb.append(this.queryEndDate == null ? "" : this.queryEndDate);
        sb.append("&operation=");
        sb.append(this.operation == null ? "" : this.operation);
        sb.append("&name=");
        sb.append(this.name == null ? "" : this.name);
        sb.append("&operator=");
        sb.append(this.operator == null ? "" : this.operator);
        return sb.toString();
    }
}
