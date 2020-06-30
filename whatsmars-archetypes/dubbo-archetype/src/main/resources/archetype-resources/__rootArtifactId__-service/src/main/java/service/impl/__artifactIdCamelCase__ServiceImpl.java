package ${package}.service.impl;

import org.apache.dubbo.config.annotation.Service;
import ${package}.api.DemoService;

@Service
public class ${artifactIdCamelCase}ServiceImpl implements ${artifactIdCamelCase}Service {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
