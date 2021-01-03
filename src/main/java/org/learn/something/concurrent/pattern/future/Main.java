package org.learn.something.concurrent.pattern.future;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        Host host = new Host();

        Data data1 = host.request('#', 20);
        Data data2 = host.request('$', 10);
        Data data3 = host.request('*', -1);

        System.out.println("Main Thread other job start");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main Thread other job end");

        try {
            System.out.println("data1.getContent() = " + data1.getContent());
            System.out.println("data2.getContent() = " + data2.getContent());
            System.out.println("data3.getContent() = " + data3.getContent());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
