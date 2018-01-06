/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.api;

import org.hongxi.whatsmars.dubbo.demo.api.vo.Bar;

public interface BarService {

	Bar findBar(String barId);

}