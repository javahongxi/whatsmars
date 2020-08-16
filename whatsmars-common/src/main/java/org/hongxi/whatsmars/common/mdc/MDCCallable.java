package org.hongxi.whatsmars.common.mdc;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class MDCCallable<V> implements Callable<V> {
  
    private final Callable<V> callable;  
  
    private transient final Map<String, String> _cm = MDC.getCopyOfContextMap();
  
    public MDCCallable(Callable<V> callable) {  
        this.callable = callable;  
    }  
  
    @Override  
    public V call() throws Exception {  
        if (_cm != null) {  
            MDC.setContextMap(_cm);  
        }  
        try {  
            return callable.call();  
        } finally {  
            MDC.clear();  
        }  
    }  
  
}  