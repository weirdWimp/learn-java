package org.learn.something.concurrent.pattern.termination;

public class Main {

    public static void main(String[] args) {
        PrintTask printTask = new PrintTask();
        printTask.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        System.out.println(Thread.currentThread().getName() + " request " + printTask.getName() + " shutdown");
        printTask.shutdown();
    }
}
