[**Kafka**](https://github.com/apache/kafka):<br>
win:<br>
修改bin/windows/kafka-server-start.bat set KAFKA_HEAP_OPTS=-Xmx512M -Xms512M<br>
server.properties port=9092<br>
启动zk zookeeper-server-start.bat ../../config/zookeeper.properties<br>
启动server kafka-server-start.bat ../../config/server.properties<br>