package org.learn.something.concurrent;


public class ThreadCooperation {

    private final Object object = new Object();

    public void waitMethod() {
        // 调用 obj.wait 方法，必须获得 obj 实例的锁
        synchronized (object) {
            try {
                object.wait(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        waitAndNotify();
    }

    public static void waitAndNotify() {
        ProductBox productBox = new ProductBox();
        new Thread(() -> productBox.produce()).start();
        new Thread(() -> productBox.consume()).start();
    }
}
