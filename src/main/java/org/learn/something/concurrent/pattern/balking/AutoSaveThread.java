package org.learn.something.concurrent.pattern.balking;

import lombok.SneakyThrows;

public class AutoSaveThread extends Thread {

    private final Data data;

    public AutoSaveThread(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true) {
                data.save();
                // 休眠约 1 秒
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
