package org.learn.something.java8;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author guo
 * @date 2021/6/8
 */
public class CompletableFutureTest {

    public static void main(String[] args) {
        CompletableFuture<List<String>> initFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " create init list");
            return IntStream.rangeClosed(1, 100).mapToObj(i -> "Seq" + i).collect(Collectors.toList());
        });

        CompletableFuture<Void> printFuture1 = initFuture.thenAcceptAsync(list -> {
            int size = list.size();
            for (int i = 0; i < size / 2; i++) {
                System.out.println(Thread.currentThread() + " print " + list.get(i));
            }
        });

        CompletableFuture<Void> printFuture2 = initFuture.thenAcceptAsync(list -> {
            int size = list.size();
            for (int i = size / 2; i < size; i++) {
                System.out.println(Thread.currentThread() + " print " + list.get(i));
            }
        });

        CompletableFuture<Object> printDoneFuture = printFuture1.thenCombineAsync(printFuture2, (v1, v2) -> {
            System.out.println(Thread.currentThread() + " all print done!!!");
            return "Good work";
        });

        CompletableFuture<Integer> sizeFuture = initFuture.thenApplyAsync(list -> {
            System.out.println(Thread.currentThread() + " list size " + list.size());
            return list.size();
        });


        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(printDoneFuture, sizeFuture);
        completableFuture.join();

        try {
            System.out.println(printDoneFuture.get());
            System.out.println(sizeFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
