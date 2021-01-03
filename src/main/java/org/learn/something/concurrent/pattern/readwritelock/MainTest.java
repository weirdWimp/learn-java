package org.learn.something.concurrent.pattern.readwritelock;

public class MainTest {

    public static void main(String[] args) {
        SharedData sharedData = new SharedData(10);
        new ReadThread(sharedData, "Thread-read-1").start();
        new ReadThread(sharedData, "Thread-read-2").start();
        new ReadThread(sharedData, "Thread-read-3").start();

        new WriteThread(sharedData, "Thread-write-1","abcdefghijklmnopqrstuvwxyz").start();
        new WriteThread(sharedData, "Thread-write-2","ABCDEFGHIJKLMNOPQRSTUVWXYZ").start();
    }

}
