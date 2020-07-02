package org.hongxi.whatsmars.initializr.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by shenhongxi on 2020/7/2.
 */
@Data
public class ProjectMeta {

    @NotEmpty
    private String type;
    @NotEmpty
    private String groupId;
    @NotEmpty
    private String artifactId;
    private String packageName;
    private boolean includeActuator;
}
