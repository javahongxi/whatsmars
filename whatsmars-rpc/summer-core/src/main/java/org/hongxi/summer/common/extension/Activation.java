package org.hongxi.summer.common.extension;

import java.lang.annotation.*;

/**
 * Spi有多个实现时，可以根据条件进行过滤、排序后再返回。
 *
 * Created by shenhongxi on 2020/7/25.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Activation {

    /** seq号越小，在返回的list<Instance>中的位置越靠前，尽量使用 0-100以内的数字 */
    int sequence() default 20;

    /** spi 的key，获取spi列表时，根据key进行匹配，当key中存在待过滤的search-key时，匹配成功 */
    String[] key() default "";

    /** 是否支持重试的时候也调用 */
    boolean retry() default true;
}
