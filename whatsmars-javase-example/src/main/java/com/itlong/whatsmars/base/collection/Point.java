package com.itlong.whatsmars.base.collection;

/**
 * Created by shenhongxi on 2016/6/14.
 */
public class Point {

    private float x;
    private float y;

    public Point(float x, float y){
        setLocation(x, y);
    }
    public void setLocation(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
