package org.learn.something.concurrent.pattern.readwritelock;

import java.util.Random;

public class WriteThread extends Thread {
    private Random random = new Random(System.currentTimeMillis());

    private final SharedData data;
    private final String filler;

    public WriteThread(SharedData data, String name, String filler) {
        super(name);
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (char c : filler.toCharArray()) {
                    data.write(c);
                    Thread.sleep(random.nextInt(3000));
                }
            }
        } catch (InterruptedException e) {
        }
    }
}
