package org.learn.something.concurrent.pattern.producerconsumer;

import java.util.Random;

public class ConsumerThread extends Thread {

    private final Random random = new Random();
    private final ItemBuffer itemBuffer;

    public ConsumerThread(ItemBuffer itemBuffer, String name) {
        super(name);
        this.itemBuffer = itemBuffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String take = itemBuffer.take();
                Thread.sleep(random.nextInt(500));
            }
        } catch (InterruptedException e) {
        }
    }
}
