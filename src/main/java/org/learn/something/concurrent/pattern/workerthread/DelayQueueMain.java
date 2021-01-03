package org.learn.something.concurrent.pattern.workerthread;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueMain {

    static class DelayImpl implements Delayed {

        private long delaySeconds;

        private long executeTime;

        public DelayImpl(long delaySeconds) {
            this.delaySeconds = delaySeconds;
            this.executeTime = System.currentTimeMillis() + delaySeconds * 1000;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.getDelay(TimeUnit.MICROSECONDS), o.getDelay(TimeUnit.MICROSECONDS));
        }

        @Override
        public String toString() {
            return "DelayImpl{" +
                    "delaySeconds=" + delaySeconds +
                    ", executeTime=" + executeTime +
                    '}';
        }
    }

    public static void main(String[] args) {

        Random random = new Random();

        DelayQueue<DelayImpl> delayQueue = new DelayQueue<>();

        for (int i = 0; i < 10; i++) {
            DelayImpl delay = new DelayImpl(5 + random.nextInt(20));
            delayQueue.put(delay);
        }

        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    DelayImpl takeObj = delayQueue.take();
                    System.out.println(takeObj);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ": Someone called interrupt method, shut down current thread");
            }
        });

        thread.start();

        try {
            Thread.sleep(10 * 1000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
