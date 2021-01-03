package org.learn.something.java8;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

public class ForkJoinTest {

    public static void main(String[] args) {
        long[] longs = LongStream.rangeClosed(0, 1000000).toArray();
        ForkJoinLongSumTask forkJoinLongSumTask = new ForkJoinLongSumTask(longs);
        System.out.println(new ForkJoinPool().invoke(forkJoinLongSumTask));

        System.out.println(LongStream.rangeClosed(0, 1000000).sum());
    }
}
