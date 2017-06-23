package com.itlong.whatsmars.base.tmp;

import java.util.*;

/**
 * Created by shenhongxi on 2015/11/9.
 * javahongxi
 */
public class T {
    public static void main(String[] args) throws Exception {
        BitSet bitSet = new BitSet(Integer.MAX_VALUE);//hashcode的值域

        String url = "http://baidu.com/a";
        int hashcode = url.hashCode() & 0x7FFFFFFF;
        bitSet.set(hashcode);

        System.out.println(bitSet.cardinality());//着色位的个数
        System.out.println(bitSet.get(hashcode));//检测存在性
        bitSet.clear(hashcode);//清除位数据
    }
}