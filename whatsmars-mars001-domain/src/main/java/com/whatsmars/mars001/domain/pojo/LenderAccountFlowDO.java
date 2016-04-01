package com.whatsmars.mars001.domain.pojo;


import java.util.Date;

/**
 * Created by jenny on 5/15/15.
 */
public class LenderAccountFlowDO {

    private Integer id;

    private int lenderId;
    //交易充值id
    private String orderId;
    //交易类型
    private int type;
    //交易金额 
    private Double amount;
    //交易前余额
    private Double balanceBefore;
    //交易后余额
    private Double balanceAfter;
    //交易前冻结资金余额
    private Double frozenBefore;
    //交易后冻结资金余额
    private Double frozenAfter;

    private Double frozen;
    //充值金额
    private Double recharge;
    //提现金额
    private Double withdraw;
    //充值手续费
    private Double fee;
    //备注
    private String description;
    //标的id
    private int biddingId;
    //还款单号
    private int billStageId;
    //检查次数
    private int checkTimes;

    private int status;

    private String courseName ;//课程名称

    private String studentName;//学生姓名

    private Integer currentStage; //还款期数

    private Integer totalStage; //还款总期数

    private Double interest; //利息

    private Double rate;//利率

    private Double principal; //本金

    private Date deadline;//截止日期

    private Date repaymentDate;//还款日期

    private String lenderRealName;

    private String lenderName;

    private String phone;

    private Date created;

    private Date modified;

    private String errorMessage; //错误信息JSON

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLenderId() {
        return lenderId;
    }

    public void setLenderId(int lenderId) {
        this.lenderId = lenderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(int biddingId) {
        this.biddingId = biddingId;
    }

    public int getBillStageId() {
        return billStageId;
    }

    public void setBillStageId(int billStageId) {
        this.billStageId = billStageId;
    }

    public int getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(int checkTimes) {
        this.checkTimes = checkTimes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Double getFrozenBefore() {
        return frozenBefore;
    }

    public void setFrozenBefore(Double frozenBefore) {
        this.frozenBefore = frozenBefore;
    }

    public Double getFrozenAfter() {
        return frozenAfter;
    }

    public void setFrozenAfter(Double frozenAfter) {
        this.frozenAfter = frozenAfter;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Integer currentStage) {
        this.currentStage = currentStage;
    }

    public Integer getTotalStage() {
        return totalStage;
    }

    public void setTotalStage(Integer totalStage) {
        this.totalStage = totalStage;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Double getFrozen() {
        return frozen;
    }

    public void setFrozen(Double frozen) {
        this.frozen = frozen;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLenderRealName() {
        return lenderRealName;
    }

    public void setLenderRealName(String lenderRealName) {
        this.lenderRealName = lenderRealName;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public Double getRecharge() {
        return recharge;
    }

    public void setRecharge(Double recharge) {
        this.recharge = recharge;
    }

    public Double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Double withdraw) {
        this.withdraw = withdraw;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

