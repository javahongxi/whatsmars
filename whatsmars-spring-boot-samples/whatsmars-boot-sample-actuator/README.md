## My Actuator Starter
- ActuatorEnvironmentPostProcessor: 设置actuator相关properties，限定用户不可修改endpoint相关属性，关闭`spring-boot-starter-actuator`默认配置
- StandardWebSecurityConfigurer: 重写Security策略
- ActuatorReporter: 上报metrics数据至时序数据库

```
curl http://localhost:8080/actuator/mappings -u application:whatsmars-spring-boot
```