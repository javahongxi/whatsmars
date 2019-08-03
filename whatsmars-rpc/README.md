# RPC
Transporter & Codec & Serialization

![img](https://github.com/javahongxi/static/blob/master/dubbo_01.jpg)

上面这张图虽然是Dubbo的remoting设计，但这个设计具有一定的通用参考性。下面解读下RocketMQ的remoting的Codec部分。

RocketMQ的通信协议的格式如下：

 4 byte   | 1+3 byte | 变长 | 变长
------ | ------|------ |------
消息长度 | 序列化类型+消息头长度 | 消息头数据 | 消息体数据


> 变长是相对定长的说法，即长度不固定

- [《Netty权威指南》](http://e.jd.com/30186249.html) `e.jd.com`
- [开发者如何玩转 RocketMQ？附最全源码解读 【Remoting篇】](https://blog.csdn.net/javahongxi/article/details/86628470)