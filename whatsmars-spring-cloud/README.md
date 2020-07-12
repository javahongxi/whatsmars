# Spring Cloud

依次启动eureka,provider,consumer,gateway
1. 直接访问 localhost:8763/hi?name=hongxi
1. 通过网关访问 localhost:8764/demo-consumer/hi?name=hongxi
1. 停掉provider，再次进行上面的访问

https://github.com/spring-cloud/spring-cloud-netflix <br>
https://github.com/spring-cloud-samples