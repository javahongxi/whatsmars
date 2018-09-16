package org.hongxi.whatsmars.javase.lambda.collection;

import java.util.function.Supplier;

/**
 * Created by shenhongxi on 2018/1/11.
 */
public interface CollectionFactory {

    static MyCollection create(Supplier<MyCollection> supplier) {
        return supplier.get();
    }
}
