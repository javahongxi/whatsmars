https://github.com/spring-cloud/spring-cloud-netflix

依次启动eureka,provider,consumer,zuul
1. 直接访问 localhost:8763/hi?name=hongxi
2. 网关访问 localhost:8764/hi?name=hongxi&accessToken=123456 localhost:8764/hi?name=hongxi
3. 停掉provider，再次进行上面的访问