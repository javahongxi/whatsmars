### 消息队列(Message Queue)

<br>RocketMQ:<br>
.properties指定rocketmqHome,namesrvAddr等，依次启动NamesrvStartup,BrokerStartup,Consumer,Producer. <br>
管理后台：rocketmq-console <br>
rocketmq原理与实践 http://wely.iteye.com/blog/2392089<br>

<br>ActiveMQ:<br>
win:下载activemq，解压执行win64/activemq.bat即启动了broker和console<br>
Mac OS X:<br>
下载 apache-activemq-5.11.1-bin.tar<br>
解压 tar -xvf apache-activemq-5.11.1-bin.tar<br>
cd bin/macosx<br>
启动broker及console ./activemq start | stop | restart<br>
访问console http://localhost:8161 conf/jetty.xml admin/admin登录<br>
查看broker日志 data/activemq.log conf/activemq.xml<br>

<br>RabbitMQ:<br>
Mac OS X:<br>
安装 drew install rabbitmq<br>
cd /usr/local/Cellar/rabbitmq/3.5.3/sbin<br>
启动broker ./rabbitmq-server<br>
看到completed with 10 plugins.标识启动OK。<br>

<br>Kafka:<br>
win:<br>
修改bin/windows/kafka-server-start.bat set KAFKA_HEAP_OPTS=-Xmx512M -Xms512M<br>
server.properties port=9092<br>
启动zk zookeeper-server-start.bat ../../config/zookeeper.properties<br>
启动server kafka-server-start.bat ../../config/server.properties<br>
