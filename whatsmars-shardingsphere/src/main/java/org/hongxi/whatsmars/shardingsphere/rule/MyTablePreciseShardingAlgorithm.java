package org.hongxi.whatsmars.shardingsphere.rule;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;

/**
 * Created by shenhongxi on 2023/5/28.
 *
 * @author shenhongxi
 */
public class MyTablePreciseShardingAlgorithm implements StandardShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        String logicTableName = preciseShardingValue.getLogicTableName();
        Long orderId = preciseShardingValue.getValue();

        return String.join("_", logicTableName, String.valueOf(orderId % 2));
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        return null;
    }
}
