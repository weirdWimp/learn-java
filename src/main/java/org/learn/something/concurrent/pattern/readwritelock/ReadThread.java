package org.learn.something.concurrent.pattern.readwritelock;

public class ReadThread extends Thread {

    private final SharedData data;

    public ReadThread(SharedData data, String name) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char[] chars = data.read();
                System.out.println(Thread.currentThread().getName() + " read:" + String.valueOf(chars));
            }
        } catch (InterruptedException e) {

        }
    }
}
