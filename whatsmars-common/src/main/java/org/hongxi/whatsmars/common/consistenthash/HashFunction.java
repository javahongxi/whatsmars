package org.hongxi.whatsmars.common.consistenthash;

/**
 * Hash String to long value
 */
public interface HashFunction {
    long hash(String key);
}
