启动后访问 http://localhost/user/ <br />

mvn clean package -DskipTests <br />
得到的jar包中的MANIFEST.MF其中几行： <br />
Main-Class: org.springframework.boot.loader.JarLauncher <br />
Start-Class: com.itlong.whatsmars.spring.boot.Application <br />
Spring-Boot-Classes: BOOT-INF/classes/ <br />
Spring-Boot-Lib: BOOT-INF/lib/ <br />

可以外部配置文件启动 <br />
java -jar whatsmars-spring-boot.jar --spring.config.location=/opt/config/application.properties <br />

shell: <br />

start.sh <br />

#!/bin/sh <br />
rm -f tpid <br />
nohup java -jar /data/app/myapp.jar --spring.profiles.active=stg > /dev/null 2>&1 & <br />
echo $! > tpid <br />

stop.sh <br />

tpid=`cat tpid | awk '{print $1}'` <br />
tpid=`ps -aef | grep $tpid | awk '{print $2}' |grep $tpid` <br />
if [ ${tpid} ]; then <br />
        kill -9 $tpid <br />
fi <br />

check.sh <br />

#!/bin/sh <br />
tpid=`cat tpid | awk '{print $1}'` <br />
tpid=`ps -aef | grep $tpid | awk '{print $2}' |grep $tpid` <br />
if [ ${tpid} ]; then <br />
        echo App is running. <br />
else <br />
        echo App is NOT running. <br />
fi <br />

kill.sh <br />

#!/bin/sh <br />
# kill -9 `ps -ef|grep 项目名称|awk '{print $2}'` <br />
kill -9 `ps -ef|grep demo|awk '{print $2}'` <br />