#!/bin/sh

app_name="whatsmars-spring-boot"
app_profiles="dev"

mvn clean package -Dmaven.test.skip=true

nohup java -Xmx1024m -Xms1024m -XX:-PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -XX:+PrintTenuringDistribution -XX:+HeapDumpOnOutOfMemoryError -Xloggc:./gc.log -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/${app_name}.jar --spring.profiles.active=${app_profiles} >./console.log 2>&1 &
