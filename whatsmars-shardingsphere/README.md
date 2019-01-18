# [ShardingSphere](http://shardingsphere.apache.org)
ShardingSphere是一套开源的分布式数据库中间件解决方案组成的生态圈，它由Sharding-JDBC、Sharding-Proxy和Sharding-Sidecar（规划中）这3款相互独立，却又能够混合部署配合使用的产品组成。它们均提供标准化的数据分片、分布式事务和数据库治理功能，可适用于如Java同构、异构语言、容器、云原生等各种多样化的应用场景。

ShardingSphere定位为关系型数据库中间件，旨在充分合理地在分布式的场景下利用关系型数据库的计算和存储能力，而并非实现一个全新的关系型数据库。它通过关注不变，进而抓住事物本质。关系型数据库当今依然占有巨大市场，是各个公司核心业务的基石，未来也难于撼动，我们目前阶段更加关注在原有基础上的增量，而非颠覆。

### sharding-jdbc 带有sharding功能的JDBC
- 封装原JDBC，完全兼容JDBC，适用于所有基于Java的ORM框架或直接使用JDBC
- 兼容各种第三方的数据库连接池，如DBCP, C3P0, BoneCP, Druid, HikariCP等
- 支持任意实现JDBC规范的数据库，目前支持MySQL, Oracle, SQLServer和PostgreSQL
- 提供分库分表、读写分离及负载均衡读等功能