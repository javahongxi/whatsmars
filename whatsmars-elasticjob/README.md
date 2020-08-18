### 分布式调度
https://shardingsphere.apache.org/elasticjob/

先启动zookeeper

#### 最佳实践
- shardingTotalCount不赋值时，即演变为master-slave模式
- 为提高worker工作效率，很多时候worker只负责异步调用服务或发MQ