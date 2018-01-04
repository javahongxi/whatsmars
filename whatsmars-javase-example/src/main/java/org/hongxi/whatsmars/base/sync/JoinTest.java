package org.hongxi.whatsmars.base.sync;

/**
 * Created by shenhongxi on 2016/8/12.
 * 子线程与主线程是顺序执行的，各子线程之间还是异步的
 */
public class JoinTest {

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(new SubRunnable(0));
        Thread t2 = new Thread(new SubRunnable(1));
        Thread t3 = new Thread(new SubRunnable(2));

        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("Main finished");
    }

    static class SubRunnable implements Runnable {
        private int id = -1;

        SubRunnable(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("hi, I'm id-" + id);
                Thread.sleep(9000);
                System.out.println(String
                        .format("Sub Thread %d finished", id));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
