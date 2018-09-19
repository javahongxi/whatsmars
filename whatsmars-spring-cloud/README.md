https://github.com/spring-cloud/spring-cloud-netflix

依次启动eureka,provider,consumer,zuul
1. 直接访问 localhost:8763/hi?name=hongxi
1. 网关访问 localhost:8764/demo/hi?name=hongxi&accessToken=123456 localhost:8764/demo/hi?name=hongxi
1. 停掉provider，再次进行上面的访问

API网关(zuul)
1. 通过 API 网关，封装微服务的 REST API。
1. 均通过 API 网关 进行调用，享受 API 网关带来的统一鉴权，统一监控等好处。
1. 通过 API 网关，控制对外暴露的 API，减轻安全隐患。

https://github.com/spring-cloud-samples