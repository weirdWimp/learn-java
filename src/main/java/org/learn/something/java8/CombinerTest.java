package org.learn.something.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guo
 * @date 2021/6/13
 */
public class CombinerTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 2;
        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();
        CompletableFuture<Integer> c = a.thenCombine(b, (y, z) -> {
            System.out.println(Thread.currentThread() + " executing task c");
            return y + z;
        });

        executorService.submit(() -> a.complete(x * x));
        executorService.submit(() -> b.complete(x + 1));

        System.out.println(c.get());
        executorService.shutdown();
    }
}
