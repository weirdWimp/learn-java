package org.learn.something.concurrent;

public class ProductBox {

    private int num = 0;

    /**
     * 调用 obj.wait 方法，必须获得 obj 实例的锁
     * synchronized 方法等同于 synchronized(this) {} 代码块
     *
     * @throws InterruptedException
     */
    public synchronized void produce() {
        while (true) {
            if (num > 0) {
                System.out.println("do not produce ... " + "waiting consume");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (num == 0) {
                num = 1;
                System.out.println("produced ... " + " notify waiting consumer thread");
                notify();
            }
        }
    }

    public synchronized void consume() {
        while (true) {
            if (num > 0) {
                num = 0;
                System.out.println("consumed ... " + " notify waiting producer thread");
                notify();
            }

            if (num == 0) {
                System.out.println("nothing to consume ... " + "waiting produce");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
