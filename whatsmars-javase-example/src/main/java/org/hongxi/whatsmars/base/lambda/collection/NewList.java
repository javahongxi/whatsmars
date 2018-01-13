package org.hongxi.whatsmars.base.lambda.collection;

/**
 * Created by shenhongxi on 2018/1/11.
 */
public class NewList implements MyCollection {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public String notRequired() {
        return "Overridden implementation";
    }
}
