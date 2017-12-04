package com.whatsmars.dbrouter;

/**
 * Created by shenhongxi on 2016/7/11.
 */
public class DbContext {

    private static final ThreadLocal<String> dbHolder = new ThreadLocal<String>();

    private static final ThreadLocal<String> tableHolder = new ThreadLocal<String>();

    public static void setDbKey(String dbKey) {
        dbHolder.set(dbKey);
    }

    public static String getDbKey() {
        return dbHolder.get();
    }

    public static void removeDbKey() {
        dbHolder.remove();
    }

    public static void setTableIndex(String tableIndex) {
        tableHolder.set(tableIndex);
    }

    public static String getTableIndex() {
        return tableHolder.get();
    }

    public static void removeTableIndex() {
        tableHolder.remove();
    }
}
