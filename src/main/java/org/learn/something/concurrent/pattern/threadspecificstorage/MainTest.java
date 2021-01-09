package org.learn.something.concurrent.pattern.threadspecificstorage;

public class MainTest {

    public static class ClientThread extends Thread {

        public ClientThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                TSLogProxy.log("i = " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            TSLogProxy.close();
        }
    }

    public static void main(String[] args) {
        new ClientThread("Alice").start();
        new ClientThread("Bob").start();
        new ClientThread("Tom").start();
    }

}
