package org.hongxi.whatsmars.dubbo.demo.provider;

import org.hongxi.whatsmars.dubbo.demo.api.BarService;
import org.hongxi.whatsmars.dubbo.demo.api.vo.Bar;

public class BarServiceMock implements BarService {

    @Override
    public Bar findBar(String barId) {
        // 你可以伪造容错数据，此方法只在出现RpcException时被执行
        return new Bar("", "容错数据", "");
    }
}