### Spring Boot 研究系列
- Servlet
- Tomcat
- Spring
- SpringMVC
- Spring Boot

启动后访问 http://localhost:8081/ <br />
swagger http://localhost:8081/swagger-ui.html

mvn clean package -DskipTests <br />
得到的jar包中的MANIFEST.MF其中几行： <br />
Main-Class: org.springframework.boot.loader.JarLauncher <br />
Start-Class: com.itlong.whatsmars.spring.boot.App <br />
Spring-Boot-Classes: BOOT-INF/classes/ <br />
Spring-Boot-Lib: BOOT-INF/lib/ <br />

可以外部配置文件启动 <br />
java -jar whatsmars-spring-boot.jar --spring.config.location=/opt/config/application.properties <br />

正确、安全地停止应用 <br />
endpoints.shutdown.enabled=true <br />
management.context-path=/manage <br />
curl -X POST host:port/manage/shutdown