package org.learn.something.concurrent.pattern.activeobject;

import java.util.concurrent.RejectedExecutionException;

public class DisplayThread extends Thread {

    private ActiveObject activeObject;

    public DisplayThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                activeObject.display(getName() + " " + i);
                Thread.sleep(200);
            }
        } catch (RejectedExecutionException | InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ":" + e);
        }
    }
}
