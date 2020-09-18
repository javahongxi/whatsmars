## Adapter
dubbo sentinel demo 见 whatsmars-dubbo-provider-boot

## transport http
`http://localhost:8719/getRules?type=flow/degrade/system`

## dashboard
1. 启动dashboard
2. 应用引入`sentinel-transport-simple-http`
3. 应用设置`-Dcsp.sentinel.dashboard.server=localhost:8080`，启动