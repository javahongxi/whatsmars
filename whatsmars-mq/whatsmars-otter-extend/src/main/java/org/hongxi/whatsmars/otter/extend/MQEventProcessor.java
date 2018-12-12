package org.hongxi.whatsmars.otter.extend;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.node.extend.processor.AbstractEventProcessor;
import com.alibaba.otter.shared.etl.model.EventColumn;
import com.alibaba.otter.shared.etl.model.EventData;
import com.alibaba.otter.shared.etl.model.EventType;
import org.hongxi.whatsmars.common.rocketmq.RocketTemplate;
import org.hongxi.whatsmars.otter.extend.support.Column;
import org.hongxi.whatsmars.otter.extend.support.OtterData;
import org.springframework.util.StringUtils;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shenhongxi on 2018/12/11.
 */
public class MQEventProcessor extends AbstractEventProcessor {

    private static final String COLUMN_ORDER_ID = "order_id";

    @Override
    public boolean process(EventData eventData) {
        EventType eventType = eventData.getEventType();
        if (eventType != EventType.INSERT
                && eventType != EventType.DELETE
                && eventType != EventType.UPDATE
                ) return false;

        OtterData otterData = new OtterData();

        String scheme = eventData.getSchemaName();
        String tableName = eventData.getTableName();
        long tableId = eventData.getTableId();
        otterData.setScheme(scheme);
        otterData.setTableName(tableName);
        otterData.setEventType(eventType);

        // 主键
        String id = "";
        for(EventColumn eventColumn : eventData.getKeys()){
            if ("id".equals(eventColumn.getColumnName())) {
                id = eventColumn.getColumnValue();
            }
        }
        otterData.setId(id);

        String msgKey = id;
        if(StringUtils.isEmpty(msgKey)){
            msgKey = tableId + "";
        }

        // 列
        List<Column> columns = new ArrayList<>();
        for(EventColumn eventColumn : eventData.getColumns()){
            Column column = doColumn(eventColumn);
            if (column != null) {
                columns.add(column);
                if ("order_id".equals(column.getName())) {
                    msgKey = column.getValue();
                }
            }
        }
        otterData.setColumns(columns);

        // topic 分库分表时不用每个库一个topic，请手动指定这里的topic
        String topic = "otter-" + scheme;
        String tags = tableName;
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher(tags);
        if (matcher.find()) {
            String suffix = matcher.group();
            tags = tags.substring(0, tags.length() - suffix.length());
        }

        RocketTemplate.sendOrderly("otter-producer", topic, tags, msgKey, JSON.toJSONString(otterData));

        return false;
    }

    private Column doColumn(EventColumn column) {
        if (column.getColumnType() != Types.BLOB && column.getColumnType() != Types.CLOB) {
            Column col = new Column();
            col.setName(column.getColumnName());
            col.setValue(column.getColumnValue());
            col.setUpdated(column.isUpdate());
            return col;
        }
        return null;
    }

}
