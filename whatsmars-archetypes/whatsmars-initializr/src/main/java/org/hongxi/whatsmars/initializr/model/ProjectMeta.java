package org.hongxi.whatsmars.initializr.model;

import lombok.Data;

/**
 * Created by shenhongxi on 2020/7/2.
 */
@Data
public class ProjectMeta {

    private String type;
    private String groupId;
    private String artifactId;
    private String packageName;
    private boolean includeActuator;
}
