/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.provider;

import org.hongxi.whatsmars.dubbo.demo.api.BarService;
import org.hongxi.whatsmars.dubbo.demo.api.vo.Bar;
import org.springframework.stereotype.Service;

@Service("barService")
public class BarServiceImpl implements BarService {

    @Override
    public Bar findBar(String barId) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        return new Bar(barId, "芝根芝底", "酒仙桥302号");
    }

}