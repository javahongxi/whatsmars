package com.whatsmars.common.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuichengrui on 2015/5/26.
 * 使用 Velocity 模板引擎快速生成文本或文件
 */
public class VelocityUtils {

    private static final String CHARSET_UTF8 = "UTF-8";

    private static final Velocity velocity = new Velocity();


    static {
        //设置从classpath中加载模板文件
        velocity.setProperty(Velocity.RESOURCE_LOADER, "class");
        velocity.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
        velocity.setProperty(Velocity.INPUT_ENCODING, CHARSET_UTF8);
        velocity.setProperty(Velocity.OUTPUT_ENCODING, CHARSET_UTF8);
        velocity.init();


    }


    public static String render(String vm, Map<String,Object> model) {
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : model.entrySet()){
            context.put(entry.getKey(), entry.getValue());
        }
        try {
            Template template = velocity.getTemplate(vm);
            StringWriter sw = new StringWriter();
            BufferedWriter writer = new BufferedWriter(sw);
            template.merge(context, writer);
            writer.flush();
            writer.close();
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("velocity to text error.");
        }
    }

    public static void main(String[] args) {
        String text = render("contract_loan.vm",new HashMap<String, Object>());
        System.out.println(text);
    }
}
