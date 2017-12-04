package com.whatsmars.base.dp.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by shenhongxi on 15/7/20.
 */
public class ObserverSample {

    static class TestObserable extends Observable{
        private String name;
        public TestObserable(){
            super();
        }

        public TestObserable(String name){
            super();
            this.name = name;
        }

        public void update(String data){
            //..
            super.setChanged();
            super.notifyObservers(data);//通知
            super.clearChanged();

        }

        @Override
        public String toString(){
            return this.name == null ? super.toString() : this.name;
        }
    }

    /**
     * 观察者
     *
     */
    static class TestObserver implements Observer {
        private String name;

        public TestObserver(String name){
            this.name = name;
        }

        @Override
        public void update(Observable o, Object arg) {
            System.out.println(name + " find " + o.toString() + " changed");
            System.out.println("changed data:" + arg.toString());

        }

    }

    public static void main(String[] args){
        TestObserver t1 = new TestObserver("t1");
        TestObserver t2 = new TestObserver("t2");
        TestObserable obserable = new TestObserable("observer");
        //顺序很重要
        obserable.addObserver(t1);
        obserable.addObserver(t2);
        obserable.update("update-test");
    }
}
