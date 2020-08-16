package org.hongxi.whatsmars.common.mdc;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public final class MDCTool {
  
    public static <T> MDCSupplier<T> supplier(Supplier<T> supplier) {
        return new MDCSupplier<>(supplier);
    }  
  
  
    public static MDCRunnable runnable(Runnable runnable) {  
        return new MDCRunnable(runnable);  
    }  
  
    public static MDCRunnable runnable(Consumer<Void> consumer) {
        return new MDCRunnable(() -> {  
            consumer.accept(null);  
        });  
    }  
  
    public static <V> MDCCallable<V> callable(Callable<V> callable) {
        return new MDCCallable<>(callable);  
    }  
  
    public static <V> MDCCallable<V> callable(Supplier<V> supplier) {  
        return new MDCCallable<>(() -> supplier.get());  
    }  
  
} 