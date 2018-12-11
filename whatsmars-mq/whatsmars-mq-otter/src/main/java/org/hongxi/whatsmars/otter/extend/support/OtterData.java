package org.hongxi.whatsmars.otter.extend.support;

import com.alibaba.otter.shared.etl.model.EventType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenhongxi on 2018/11/23.
 */
@Data
public class OtterData implements Serializable {

    private String scheme;

    private EventType eventType;

    private String tableName;

    private String id;

    private List<Column> columns;
}
