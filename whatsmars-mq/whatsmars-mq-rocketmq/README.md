# [Apache RocketMQ](https://github.com/apache/rocketmq)
分布式消息中间件

### Test
- .properties指定rocketmqHome,namesrvAddr等，依次启动NamesrvStartup,BrokerStartup,Consumer,Producer
- 管理后台：https://github.com/apache/rocketmq-externals

### User Guide
- 每个主题可设置队列个数，默认4个，需要顺序消费的消息发往同一队列，比如同一订单号相关的几条需要顺序消费的消息发往同一队列，
顺序消费的特点的是，不会有两个消费者共同消费任一队列，且当消费者数量小于队列数时，消费者会消费多个队列。至于消息重复，在消
费端处理。事务消息可参考rocketmq的做法，也可直接用本地事务。
- Broker上存Topic信息，Topic由多个队列组成，队列会平均分散在多个Broker上，而Producer的发送机制保证消息尽量平均分布到
所有队列中，最终效果就是所有消息都平均落在每个Broker上。
- ConsumerQueue相当于CommitLog的索引文件，消费者消费时会先从ConsumerQueue中查找消息在commitLog中的offset，再去
CommitLog中找元数据。如果某个消息只在CommitLog中有数据，没在ConsumerQueue中， 则消费者无法消费，RocketMQ的事务消息就
是这个原理。

### More
[RocketMQ架构模块解析](https://blog.csdn.net/javahongxi/article/details/72956608)