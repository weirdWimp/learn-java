package org.learn.something.concurrent.pattern.termination;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchMain {

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    System.out.println(Thread.currentThread().getName() + " " + (j + 1));
                }
                countDownLatch.countDown();
            }, "PrintThread" + i).start();
        }

        try {
            System.out.println("主线程等待所有线程打印完毕中....");
            countDownLatch.await();
            System.out.println("主线程等待所有线程打印完毕结束");
        } catch (InterruptedException e) {
        }


    }

}
