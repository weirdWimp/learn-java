package org.learn.something.concurrent.pattern.immutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizeListTest {

    public static class WriteThread extends Thread {

        private List<Integer> backList;

        public WriteThread(List<Integer> backList) {
            this.backList = backList;
        }

        @Override
        public void run() {
            for (int i = 0; ; i++) {
                backList.add(i);
                backList.remove(0);
            }
        }
    }

    public static class ReadThread extends Thread {

        private List<Integer> backList;

        public ReadThread(List<Integer> backList) {
            this.backList = backList;
        }

        /**
         * Must be manually synched by user!
         *
         * @see java.util.Collections.SynchronizedCollection#iterator()
         */
        @Override
        public void run() {
            synchronized (backList) {
                while (true) {
                    for (Integer integer : backList) {
                        System.out.println(integer);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        List<Integer> numbers = Collections.synchronizedList(new ArrayList<Integer>());
        new WriteThread(numbers).start();
        new ReadThread(numbers).start();
    }

}
