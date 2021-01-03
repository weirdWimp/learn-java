package org.learn.something.concurrent.pattern.workerthread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceMain {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName("PersistJobThreadPool-" + thread.getName());
            return thread;
        });

        System.out.println("ExecutorService has been initialized");

        for (int i = 0; i < 10; i++) {
            Runnable task = () -> System.out.println(Thread.currentThread().getName() + " is executing");
            executorService.execute(task);
        }

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }
}
