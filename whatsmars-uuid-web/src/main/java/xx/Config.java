package xx;

/**
 * Created by shenhongxi on 2016/8/12.
 */
public class Config {

    /**
     * 业务名称
     */
    private String name;
    /**
     * 内存运算个数
     */
    private int cacheSize;
    /**
     * 生成自增数长度
     */
    private int length;
    /**
     * 填充字符
     */
    private String fillChar;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * 自增预警数
     */
    private long warnNum;
    /**
     * 重置数
     */
    private long resetNum;
    /**
     * 1 uuid=前缀+自增数+后缀
     */
    private int strategy;

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getFillChar() {
        return fillChar;
    }

    public void setFillChar(String fillChar) {
        this.fillChar = fillChar;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public long getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(long warnNum) {
        this.warnNum = warnNum;
    }

    public long getResetNum() {
        return resetNum;
    }

    public void setResetNum(long resetNum) {
        this.resetNum = resetNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }
}
