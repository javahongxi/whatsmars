package org.hongxi.whatsmars.initializr.controller;

import org.apache.commons.io.FileUtils;
import org.hongxi.whatsmars.initializr.model.ProjectMeta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.File;

/**
 * Created by shenhongxi on 2020/7/2.
 */
@Controller
@RequestMapping("/project")
public class InitializrController {

    private static final String CMD = "sh generate.sh ";

    @PostMapping
    public ResponseEntity<byte[]> initialize(@Valid ProjectMeta projectMeta) throws Exception {
        if (StringUtils.isEmpty(projectMeta.getPackageName())) {
            projectMeta.setPackageName(projectMeta.getGroupId());
        }
        StringBuilder params = new StringBuilder(projectMeta.getType());
        params.append(" ").append(projectMeta.getGroupId())
                .append(" ").append(projectMeta.getArtifactId())
                .append(" ").append(projectMeta.getPackageName())
                .append(" ").append(projectMeta.isIncludeActuator());

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(CMD + params);
            process.waitFor();

            String dst = projectMeta.getArtifactId() + ".tar";
            byte[] content = FileUtils.readFileToByteArray(new File(dst));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", projectMeta.getArtifactId() + ".tar");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
