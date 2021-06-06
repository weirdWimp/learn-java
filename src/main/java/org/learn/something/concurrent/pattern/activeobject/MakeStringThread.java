package org.learn.something.concurrent.pattern.activeobject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public class MakeStringThread extends Thread {

    private ActiveObject activeObject;

    public MakeStringThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Future<String> future = activeObject.mkString(getName().charAt(0), i + 1);
                Thread.sleep(200);
                System.out.println(getName() + " mkString:" + future.get());
            }
        } catch (RejectedExecutionException | InterruptedException | ExecutionException e) {
            System.out.println(getName() + ":" + e);
        }
    }
}
