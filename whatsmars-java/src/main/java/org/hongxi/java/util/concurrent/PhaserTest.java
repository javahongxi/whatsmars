package org.hongxi.java.util.concurrent;

import java.util.concurrent.Phaser;

/**
 * @author shenhongxi 2019/8/13
 * @see java.util.concurrent.CountDownLatch
 * @see java.util.concurrent.CyclicBarrier
 */
public class PhaserTest {

    public static void main(String[] args) {
        int swimmers = 6;
        Phaser phaser = new SwimmingPhaser();
        phaser.register();
        for (int i = 0; i < swimmers; i++) {
            phaser.register();
            new Thread(new Swimmer(phaser), "swimmer" + i).start();
        }
        phaser.arriveAndDeregister();

        while (!phaser.isTerminated()) { }
        System.out.println("比赛结束");
    }

    static class SwimmingPhaser extends Phaser {
        private int phaseToTerminate = 2;

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            System.out.println("*第" + phase + "阶段完成*");
            return phase == phaseToTerminate || registeredParties == 0;
        }
    }

    static class Swimmer implements Runnable {
        Phaser phaser;

        Swimmer(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println("游泳选手" + Thread.currentThread().getName() + "已到达赛场");
            phaser.arriveAndAwaitAdvance();

            System.out.println("游泳选手" + Thread.currentThread().getName() + "已准备好");
            phaser.arriveAndAwaitAdvance();

            System.out.println("游泳选手"+Thread.currentThread().getName()+"完成比赛");
            phaser.arriveAndAwaitAdvance();
        }
    }
}
