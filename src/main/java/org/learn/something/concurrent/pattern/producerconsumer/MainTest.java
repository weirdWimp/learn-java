package org.learn.something.concurrent.pattern.producerconsumer;

public class MainTest {
    public static void main(String[] args) {
        ItemBuffer itemBuffer = new ItemBuffer(3);

        new ConsumerThread(itemBuffer, "Thread-Consumer-1").start();
        new ConsumerThread(itemBuffer, "Thread-Consumer-2").start();
        new ConsumerThread(itemBuffer, "Thread-Consumer-3").start();

        new ProducerThread(itemBuffer, "Thread-Producer-1").start();
        new ProducerThread(itemBuffer, "Thread-Producer-2").start();
        new ProducerThread(itemBuffer, "Thread-Producer-3").start();
    }
}
