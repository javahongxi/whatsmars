### 针对官方starter修改点 [官方](https://github.com/apache/rocketmq-spring)
- 支持连接多个集群(订阅) （官方一个应用只能连接一个集群）
- 顺序消息消费失败，可配重试次数 （非顺序消息默认重试16次，每次时间延后）
- 发送延时消息方法参数优化(魔法参数改为枚举)
- 优化getMessageType方法，支持 MyConsumer extends AbstractConsumer implements RocketMQListener <br>（官方只支持MyConsumer implements RocketMQListener）
- RocketMQTemplate方法重载(加入keys)
- 暂未加入事务消息功能 （官方最新版支持）

当需要往其他集群发送消息时，需要拷贝一份`RocketMQAutoConfiguration`和`RocketMQProperties`，
并相应修改@Bean方法名和Conditional，最重要的是给MQProducer设置instanceName，且默认的MQProducer也需要手动实例化。