package org.hongxi.summer.common.util;

/**
 * Created by shenhongxi on 2020/7/28.
 */
public class MathUtils {

    /**
     * 针对int类型字符串进行解析，如果存在格式错误，则返回默认值（defaultValue）
     * Parse intStr, return defaultValue when numberFormatException occurs
     * @param intStr
     * @param defaultValue
     * @return
     */
    public static int parseInt(String intStr, int defaultValue) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 针对long类型字符串进行解析，如果存在格式错误，则返回默认值（defaultValue）
     * Parse longStr, return defaultValue when numberFormatException occurs
     * @param longStr
     * @param defaultValue
     * @return
     */
    public static long parseLong(String longStr, long defaultValue){
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 通过二进制位操作将originValue转化为非负数:
     *      0和正数返回本身
     *      负数通过二进制首位取反转化为正数或0（Integer.MIN_VALUE将转换为0）
     * return non-negative int value of originValue
     * @param originValue
     * @return positive int
     */
    public static int getNonNegative(int originValue){
        return 0x7fffffff & originValue;
    }

    /**
     * 通过二进制位操作将originValue转化为非负数:
     * 范围在[0-16777215] 之间
     *
     * @param originValue
     * @return
     */
    public static int getNonNegativeRange24bit(int originValue) {
        return 0x00ffffff & originValue;
    }
}
