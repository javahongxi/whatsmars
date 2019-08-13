package org.hongxi.java.util.concurrent;

/**
 * @author shenhongxi 2019/8/13
 */
public class BlockingStackTest {

    public static void main(String[] args) {
        BlockingStack<String> stack = new BlockingStack<>(10);

        new Thread(new Producer(stack, "p1")).start();
        new Thread(new Consumer(stack, "c1")).start();
        new Thread(new Producer(stack, "p2")).start();
        new Thread(new Consumer(stack, "c2")).start();
    }

    static class Consumer implements Runnable {
        private BlockingStack stack;
        private String name;

        public Consumer(BlockingStack stack, String name) {
            this.stack = stack;
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 60; i++) {
                Object o = stack.pop();
                System.err.println(name + " consume: " + o);
                try {
                    Thread.sleep((long) (Math.random() * 400));
                } catch (InterruptedException e) {}
            }
        }
    }

    static class Producer implements Runnable {
        private BlockingStack stack;
        private String name;

        public Producer(BlockingStack stack, String name) {
            this.stack = stack;
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 60; i++) {
                String data = name + "-" + i;
                stack.push(data);
                System.out.println(name + " produce: " + data);
                try {
                    Thread.sleep((long) (Math.random() * 100));
                } catch (InterruptedException e) {}
            }
        }
    }
}
