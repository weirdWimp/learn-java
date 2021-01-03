package org.learn.something.concurrent.pattern.guardedsuspension;

import java.util.Random;

public class BasicGuardedSuspensionTest {

    private static class ServerThread extends Thread {

        private BasicRequestQueue requests;
        private Random random;

        public ServerThread(String name, long seed, BasicRequestQueue requests) {
            super(name);
            this.requests = requests;
            this.random = new Random(seed);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Request request = requests.getRequest();
                System.out.printf("Thread Name: %s, get request: %s\n", getName(), request);

                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    // has nothing to do with InterruptedException
                }
            }
        }
    }


    private static class ClientThread extends Thread {

        private BasicRequestQueue requests;
        private Random random;

        public ClientThread(String name, long seed, BasicRequestQueue requests) {
            super(name);
            this.requests = requests;
            this.random = new Random(seed);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Request request = new Request("No." + i);
                System.out.printf("Thread Name: %s, put request: %s\n", getName(), request);
                requests.putRequest(request);
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    // has nothing to do with InterruptedException
                }
            }
        }
    }


    public static void main(String[] args) {
        BasicRequestQueue requestQueue = new BasicRequestQueue();
        new ClientThread("client1", 21321L, requestQueue).start();
        new ServerThread("server1", 2139012L, requestQueue).start();
    }

}
