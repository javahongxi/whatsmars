启动后访问 http://localhost/user/ <br />

mvn package -DskipTests <br />
得到的jar包中的MANIFEST.MF如下： <br />
Manifest-Version: 1.0 <br />
Implementation-Title: whatsmars-spring-boot <br />
Implementation-Version: 1.5.2.RELEASE <br />
Archiver-Version: Plexus Archiver <br />
Built-By: shenhongxi <br />
Implementation-Vendor-Id: org.springframework.boot <br />
Spring-Boot-Version: 1.5.2.RELEASE <br />
Implementation-Vendor: Pivotal Software, Inc. <br />
Main-Class: org.springframework.boot.loader.JarLauncher <br />
Start-Class: com.itlong.whatsmars.spring.boot.Application <br />
Spring-Boot-Classes: BOOT-INF/classes/ <br />
Spring-Boot-Lib: BOOT-INF/lib/ <br />
Created-By: Apache Maven 3.3.1 <br />
Build-Jdk: 1.8.0_131 <br />
Implementation-URL: http://maven.apache.org <br />