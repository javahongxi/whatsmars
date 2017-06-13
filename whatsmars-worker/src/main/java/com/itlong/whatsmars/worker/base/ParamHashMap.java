package com.itlong.whatsmars.worker.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2016/7/13.
 */
public class ParamHashMap<K,V> extends HashMap<K,V> {

    private static final String TABLE_SUFFIX = "tableIndex";

    @SuppressWarnings( "unchecked" )
    public ParamHashMap(){
        super();
        this.put((K) TABLE_SUFFIX, (V) DbContext.getTableIndex());
    }
    @SuppressWarnings( "unchecked" )
    public ParamHashMap(Map<? extends K, ? extends V> m){
        super(m);
        this.put((K)TABLE_SUFFIX, (V)DbContext.getTableIndex());
    }
    @SuppressWarnings( "unchecked" )
    public ParamHashMap(int initialCapacity) {
        super(initialCapacity);
        this.put((K)TABLE_SUFFIX, (V)DbContext.getTableIndex());
    }
    @SuppressWarnings( "unchecked" )
    public ParamHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity,loadFactor);
        this.put((K)TABLE_SUFFIX, (V) DbContext.getTableIndex());
    }

    private static final long serialVersionUID = 5541751367713832209L;

}

