package org.learn.something.concurrent.pattern.termination;

import java.util.concurrent.*;

public class CyclicBarrierMain {

    private static final class MyWork implements Runnable {

        private static final int PHASE = 5;

        private CountDownLatch countDownLatch;
        private CyclicBarrier cyclicBarrier;

        public MyWork(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier) {
            this.countDownLatch = countDownLatch;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < PHASE; i++) {
                    doPhase(i);
                    cyclicBarrier.await();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }

        private void doPhase(int phase) {
            System.out.println(Thread.currentThread().getName() + " finished phase " + phase);
        }

    }


    public static void main(String[] args) {
        int threads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threads, () -> {
            System.out.println("\n" + Thread.currentThread().getName() + " barrier action!\n");
        });


        for (int i = 0; i < threads; i++) {
            executorService.execute(new MyWork(countDownLatch, cyclicBarrier));
        }

        try {
            System.out.println("AWAIT");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            System.out.println("END");
        }


    }

}
