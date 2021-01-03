package org.learn.something.concurrent.pattern.balking;

import java.util.Random;

public class ChangeThread extends Thread {

    private final Data data;

    private Random random = new Random(System.currentTimeMillis());

    public ChangeThread(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; ; i++) {
                data.change("Content: No." + i);
                Thread.sleep(random.nextInt(1000));
                data.save();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
