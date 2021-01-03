package org.learn.something.concurrent;

import java.util.concurrent.ThreadFactory;

public class   CreateThread {

    public static void main(String[] args) {
        threadFactory();
    }

    private static void thread() {
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "thread executing");
            }
        }.start();
    }

    private static void runnable() {
        new Thread(() -> System.out.println(Thread.currentThread() + "thread executing")).start();
    }

    /**
     * 根据需要创建新的线程，可以统一设置线程的分组，命名等特殊化需求
     */
    private static class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(thread.getName() + " CreatedByMyThreadFactory");
            return thread;
        }
    }

    private static void threadFactory() {
        ThreadFactory myThreadFactory = new MyThreadFactory();
        for (int i = 0; i < 5; i++) {
            myThreadFactory.newThread(() -> System.out.println(Thread.currentThread().getName() + " executing")).start();
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " executing");
        }

        // Thread-0 CreatedByMyThreadFactory executing
        // Thread-3 CreatedByMyThreadFactory executing
        // Thread-4 CreatedByMyThreadFactory executing
        // main executing
        // main executing
        // main executing
        // main executing
        // main executing
        // Thread-1 CreatedByMyThreadFactory executing
        // Thread-2 CreatedByMyThreadFactory executing
    }
}
