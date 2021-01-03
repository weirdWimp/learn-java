package org.learn.something.concurrent.pattern.future;

public class Interrupted {

    public static void main(String[] args) {


        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);

                // if any thread has interrupted the current thread.
                // The interrupted status of the current thread is cleared when this exception is thrown.
            } catch (InterruptedException e) {
                // 中断状态已经被清除
                System.out.println(Thread.currentThread() + " has been interrupted");
                System.out.println(Thread.currentThread() + "'s interrupted status: " + Thread.currentThread().isInterrupted());
            }
        });

        thread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
