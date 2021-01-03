package org.learn.something.concurrent.pattern.producerconsumer;

public class ItemBuffer {

    private final String[] buffer;

    private int nextPut = 0;
    private int nextTake = 0;
    private int count = 0;

    public ItemBuffer(int size) {
        this.buffer = new String[size];
    }

    public synchronized void put(String item) throws InterruptedException {
        while (count >= buffer.length) {
            wait();
        }
        System.out.println(Thread.currentThread().getName() + " make item:" + item);
        buffer[nextPut] = item;
        nextPut = (nextPut + 1) % buffer.length;
        count++;
        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (count <= 0) {
            wait();
        }
        String item = buffer[nextTake];
        System.out.println(Thread.currentThread().getName() + " get item: " + item);
        nextTake = (nextTake + 1) % buffer.length;
        count--;
        notifyAll();
        return item;
    }
}
