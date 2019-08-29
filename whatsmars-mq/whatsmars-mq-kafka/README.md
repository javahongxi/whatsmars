# [Kafka](https://github.com/apache/kafka):

1. 启动zk org.hongxi.whatsmars.zk.ZKStartup
1. 启动kafka org.hongxi.whatsmars.kafka.KafkaStartup
1. 启动client org.hongxi.whatsmars.kafka.KafkaApplication

linux:<br>
启动zk ./zookeeper-server-start.sh ../config/zookeeper.properties<br>
启动server ./kafka-server-start.sh ../config/server.properties<br>

win:<br>
修改bin/windows/kafka-server-start.bat set KAFKA_HEAP_OPTS=-Xmx512M -Xms512M<br>
server.properties port=9092<br>
启动zk zookeeper-server-start.bat ../../config/zookeeper.properties<br>
启动server kafka-server-start.bat ../../config/server.properties<br>

[《Apache Kafka 源码剖析》](https://e.jd.com/30352947.html)
