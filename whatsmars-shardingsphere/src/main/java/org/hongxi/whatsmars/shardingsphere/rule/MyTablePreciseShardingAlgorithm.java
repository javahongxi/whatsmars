package org.hongxi.whatsmars.shardingsphere.rule;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * Created by shenhongxi on 2023/5/28.
 *
 * @author shenhongxi
 */
public class MyTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        String logicTableName = preciseShardingValue.getLogicTableName();
        Long orderId = preciseShardingValue.getValue();

        return String.join("_", logicTableName, String.valueOf(orderId % 2));
    }
}
