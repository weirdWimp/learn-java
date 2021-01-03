package org.learn.something.concurrent.pattern.producerconsumer;

import java.util.Random;

public class ProducerThread extends Thread {

    private final Random random = new Random();

    private final ItemBuffer itemBuffer;

    private static int id = 0;

    public ProducerThread(ItemBuffer itemBuffer, String name) {
        super(name);
        this.itemBuffer = itemBuffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000));
                String item = "Item No." + nextId();
                itemBuffer.put(item);
            }
        } catch (InterruptedException e) {
        }
    }

    public static synchronized int nextId() {
        return id++;
    }
}
