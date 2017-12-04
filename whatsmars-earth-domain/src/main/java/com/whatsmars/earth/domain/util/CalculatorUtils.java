package com.whatsmars.earth.domain.util;

import com.whatsmars.earth.domain.enums.FeeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenguang on 2015/5/7 0007.
 *
 */
public class CalculatorUtils {

    private static final Double PERCENT = 100.00d;

    /**
     * 宽恕期内每月利息
     * @param principal
     * @return
     */
    public static Double gracePeriodInterest(Double principal, double yearRate) {
        double interest = principal * yearRate / 1200.00;
        return format(interest);
    }

    /**
     * 宽恕期内每月总费（对应新方案：宽恕期内、宽恕期外都有服务费）
     * @param principal 本金
     * @param yearRate 年利率
     * @param graceRate 宽恕期内费率
     * @return
     */
    public static Double gracePeriod(double principal,double yearRate,double graceRate){
        double interest = ArithUtils.round(ArithUtils.div(ArithUtils.mul(principal,yearRate),12*PERCENT),2);
        double fee =calculateGraceFee(principal,graceRate);
        return ArithUtils.add(interest, fee);
    }


    /**
     * 计算等额本息,最终得出每个月需要偿还（本金：利息）
     * @param months
     * @param principal
     * @param yearInterestRate 年利率，数字，非百分比
     * @return 按月份排序，key为本金，value为利息
     */
    public static List<Map.Entry<Double,Double>> calculate(int months, Double principal, Double yearInterestRate) {
        double monthInterestRate = new BigDecimal(yearInterestRate / 1200).doubleValue();
        Double principalAndInterest = format((principal * monthInterestRate * Math.pow((1 + monthInterestRate), months))
                / ( Math.pow((1 + monthInterestRate), months) - 1));//月供
        List<Map.Entry<Double,Double>> result = new ArrayList<Map.Entry<Double, Double>>(months * 2);
        //等额本息
        double leftPrincipal = principal;
        Map<Double,Double> _map = new HashMap<Double, Double>(4);
        for(int i = 1; i <= months; i++) {
            _map.clear();
            Double interestOfMonth = format(leftPrincipal * monthInterestRate);//本期偿还利息
            Double principalOfMonth = 0d;

            //如果是最后一期
            if(i == months) {
                principalOfMonth = leftPrincipal;    //最后一期的本金，为未还本金
                interestOfMonth = format(principalAndInterest - principalOfMonth);  //最后一期的利息为，月供-最后一期本金
            } else {
                principalOfMonth = principalAndInterest - interestOfMonth;//本期偿还本金
                leftPrincipal  -= principalOfMonth;//期末剩余本金
            }
            _map.put(format(principalOfMonth),interestOfMonth);
            result.add(_map.entrySet().iterator().next());
        }
        return result;
    }

    /**
     * 等额本息期月供,仅包括（本金+利息）
     * @param months 等额本息期数
     * @param principal 借款总额
     * @param yearInterestRate 年利率
     * @return
     */
    public static double monthPay(int months, Double principal, Double yearInterestRate) {
        double monthRate = new BigDecimal(yearInterestRate / 1200).doubleValue();
        return format((principal * monthRate * Math.pow((1 + monthRate), months))
                / ( Math.pow((1 + monthRate), months) - 1));
    }

    /**
     * 计算标的总收益
     * @param principal 本金
     * @param rate 年利率
     * @param monthLimit 借款期限
     * @param gracePeriod 宽恕期
     * @return
     */
    public static double calculateInterest(double principal, double rate, int monthLimit, int gracePeriod) {
        double totalPeriodInterest = gracePeriodInterest(principal,rate) * gracePeriod;
        int months = monthLimit - gracePeriod ;
        double totalMonthPay = monthPay(months,principal,rate) * months;
        return format(totalMonthPay + totalPeriodInterest - principal);
    }

    /**
     * 学生充值，计算交易总额：输入金额 + 手续费
     * @return
     */
    public static Double rechargeAndFee(double amount) {
        double total = amount + rechargeFee(amount);
        return format(total);
    }

    /**
     * 学生充值计算手续费
     * @param amount
     * @return
     */
    public static Double rechargeFee(double amount) {
        return format(amount * FeeEnum.RECHARGE.code/100);
    }


    public static double format(double value){
        if (value == Double.valueOf(value).intValue()){
            return Double.valueOf(value).intValue();
        }else {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * * 计算宽恕期外月服务费率
     * @param gracePeriod 宽恕期
     * @param monthLimit  借款期限
     * @param graceRate 宽恕期内月服务费率 数字 非百分比
     * @param feeRate 月基础服务费率 数字 非百分比
     * @return 宽恕期外月服务费率，数字，非百分比
     * @throws Exception
     */
    public static Double calculateMonthFeeRate(int gracePeriod,int monthLimit,double graceRate,double feeRate)throws Exception{
        if(gracePeriod >= monthLimit){
            throw new RuntimeException("请求参数错误,宽恕期限小于借款期限");
        }
        double totalFee = ArithUtils.mul(monthLimit,feeRate);
        double graceFee = ArithUtils.mul(gracePeriod,graceRate);
        if(graceFee > totalFee){
            throw new RuntimeException("请求参数错误,宽恕期费率*宽恕期限 <= 总费率*借款期限");
        }
        double monthFee = ArithUtils.sub(totalFee, graceFee);
        return ArithUtils.div(monthFee, monthLimit - gracePeriod);
    }

    /**
     * 计算宽恕期内月服务费
     * @param principal 本金
     * @param graceRate 宽恕期内月服务费率
     * @return 宽恕期内月服务费
     */
    public static Double calculateGraceFee(double principal,double graceRate){
        double _graceRate = ArithUtils.div(graceRate, PERCENT);
        return ArithUtils.round(ArithUtils.mul(principal, _graceRate), 2);
    }

    /**
     * 计算宽恕期内总服务费
     * @param principal 本金
     * @param graceRate 宽恕期内月服务费率，非百分比
     * @param gracePeriod 宽恕期
     * @return 宽恕期内服务费
     */
    public static  Double calculateGraceTotalFee(double principal,double graceRate,int gracePeriod){
        double monthGraceFee = calculateGraceFee(principal, graceRate);
        return ArithUtils.mul(monthGraceFee,gracePeriod);
    }

    /**
     * 计算宽恕期外月服务费
     * @param principal 本金
     * @param gracePeriod 宽恕期
     * @param monthLimit  借款期限
     * @param graceRate 宽恕期内月服务费率 数字 非百分比
     * @param feeRate 月基础服务费率 数字 非百分比
     * @return 宽恕期外月服务费
     */
    public  static Double calculateMonthFee(double principal,int gracePeriod,int monthLimit,double graceRate,double feeRate){
        try{
            double monthRate = calculateMonthFeeRate(gracePeriod,monthLimit,graceRate,feeRate);
            double _monthRate = ArithUtils.div(monthRate,PERCENT);
            return ArithUtils.round(ArithUtils.mul(principal,_monthRate),2);
        }catch (Exception e ){
            throw new RuntimeException("计算宽恕期外月服务费异常");
        }
    }

    /**
     * 计算宽恕期外总服务费
     * @param principal 本金
     * @param graceRate 宽恕期内月服务费率，非百分比
     * @param feeRate 月基础服务费率 数字 非百分比
     * @param monthLimit 借款期限
     * @param gracePeriod 宽恕期限
     * @return 宽恕起外服务费
     */
    public static Double calculateMonthTotalFee(double principal,double graceRate,double feeRate,int monthLimit,int gracePeriod){
        Double totalFee = calculateTotalFee(principal, feeRate, monthLimit);
        Double graceFee = calculateGraceTotalFee(principal, graceRate, gracePeriod);
        return ArithUtils.sub(totalFee,graceFee);
    }

    /**
     * 计算总服务费
     * @param principal 本金
     * @param feeRate 月基础服务费率 数字非百分比
     * @param monthLimit 借款期限
     * @return 总服务费
     */
    public static Double calculateTotalFee(double principal,double feeRate,int monthLimit){
        double _feeRate = ArithUtils.div(feeRate, PERCENT);
        double monthFee = ArithUtils.mul(principal, _feeRate);
        return  ArithUtils.mul(monthFee,monthLimit);
    }

    /**
     * 计算最后一期服务费（按照减法来做）
     * @param principal 本金
     * @param feeRate 月基础服务费率 数字非百分比
     * @param monthLimit 借款期限
     * @param gracePeriod 宽恕期
     * @param graceRate 宽恕期内月服务费率 数字非百分比
     * @return 宽恕期最后一期服务费
     */
    public static Double calculateLastMonthFee(double principal,double feeRate,int monthLimit,int gracePeriod,double graceRate){
        double monthTotalFee = calculateMonthTotalFee(principal,graceRate,feeRate,monthLimit,gracePeriod);
        double monthFee = calculateMonthFee(principal,gracePeriod,monthLimit,graceRate,feeRate);
        double monthLastFee = ArithUtils.sub(monthTotalFee, ArithUtils.mul(monthFee, monthLimit - gracePeriod - 1));
        return monthLastFee;
    }

    /**
     * TEST   测试
     * @param args
     */
    public static void main(String[] args) {
        //借款30000,12期，宽恕期3个月，年利率12
        double principal = 17800;
        int monthLimit = 24;
        int gracePeriod = 6;
        double feeRate = 0.33;
        double graceRate = 0.29;   //宽恕期费率
        double monthRate = 0.0d;  //基本月费率
                try{
                    monthRate = calculateMonthFeeRate(gracePeriod,monthLimit,graceRate,feeRate);
                    double monthFee = calculateMonthFee(principal,gracePeriod,monthLimit,graceRate,feeRate);
                    double totalFee = calculateTotalFee(principal,feeRate,monthLimit);
                    //宽恕期外服务费率
                    System.out.println("宽恕期外服务费率：" + monthRate);
                    System.out.println("宽恕期外月服务费：" + monthFee);
                    System.out.println("总服务费：" + totalFee);
                    //宽恕期内利息
                    System.out.println("宽恕期内利息："+gracePeriodInterest(principal, feeRate));
                    //宽恕期内服务费
                    System.out.println("宽恕期内月服务费："+calculateGraceFee(principal, graceRate));
                    //最后一个月服务费
                    System.out.println("最后一个月服务费：" + calculateLastMonthFee(principal,feeRate,monthLimit,gracePeriod,graceRate));

                    //宽恕期内总服务费
                    System.out.println("宽恕期内总服务费："+calculateGraceTotalFee(principal,graceRate,gracePeriod));
                    //宽恕期外总服务费
                    System.out.println("宽恕期外总服务费："+calculateMonthTotalFee(principal,graceRate,feeRate,monthLimit,gracePeriod));

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }


        /*
        //宽恕期利息
        double interest = gracePeriodInterest(principal,rate);
        //宽恕期管理费
        double fee = gracePeriodFee(principal,monthLimit,gracePeriod);
        //宽恕期每月总费用
        double periodAmount = gracePeriod(principal,monthLimit,gracePeriod,rate);
        //等额本息月供
        double monthPay = monthPay(monthLimit - gracePeriod,principal,rate);
        //标的投资人总收益
        double totalInterest = calculateInterest(principal,rate,monthLimit,gracePeriod);

        System.out.println("宽恕期每月利息："+interest);
        System.out.println("宽恕期每月管理费："+fee);
        System.out.println("宽恕期每月总费用："+periodAmount);
        System.out.println("等额本息月供："+monthPay);
        System.out.println("标的投资人总收益"+totalInterest);
        System.out.println("学好贷总服务费："+format(fee * gracePeriod));

        List<Map.Entry<Double,Double>> list = calculate(monthLimit,principal,rate);
        int i = 0;
        for(Map.Entry<Double, Double> map: list){
            System.out.println("--------------"+(++i)+"----------------");
            System.out.println(map.getKey() + "  " + map.getValue());
        }
        */
    }
}
