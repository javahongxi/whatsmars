package org.hongxi.whatsmars.base.thread;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

//不能改动此Test类	
public class CopyOnWriteArrayListTest extends Thread {

    private TestDo testDo;
    private String key;
    private String value;

    public CopyOnWriteArrayListTest(String key, String key2, String value) {
        this.testDo = TestDo.getInstance();
        /*
         * 常量"1"和"1"是同一个对象，下面这行代码就是要用"1"+""的方式产生新的对象，
         * 以实现内容没有改变，仍然相等（都还为"1"），但对象却不再是同一个的效果
         */
        this.key = key + key2;
        /*
         * a = "1"+""; b = "1"+""
         */
        this.value = value;
    }

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayListTest a = new CopyOnWriteArrayListTest("1", "", "1");
        CopyOnWriteArrayListTest b = new CopyOnWriteArrayListTest("1", "", "2");
        CopyOnWriteArrayListTest c = new CopyOnWriteArrayListTest("3", "", "3");
        CopyOnWriteArrayListTest d = new CopyOnWriteArrayListTest("4", "", "4");
        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
        a.start();
        b.start();
        c.start();
        d.start();

    }

    public void run() {
        testDo.doSome(key, value);
    }
}

class TestDo {

    private TestDo() {
    }

    private static TestDo _instance = new TestDo();

    public static TestDo getInstance() {
        return _instance;
    }

    // private ArrayList keys = new ArrayList();
    private CopyOnWriteArrayList<Object> keys = new CopyOnWriteArrayList<Object>();

    public void doSome(Object key, String value) {
        Object o = key;
        if (!keys.contains(o)) {
            keys.add(o);
        } else {

            for (Iterator<Object> iter = keys.iterator(); iter.hasNext();) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Object oo = iter.next();
                if (oo.equals(o)) {
                    o = oo;
                    break;
                }
            }
        }
        synchronized (o)
        // 以大括号内的是需要局部同步的代码，不能改动!
        {
            try {
                Thread.sleep(1000);
                System.out.println(key + ":" + value + ":"
                        + (System.currentTimeMillis() / 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
