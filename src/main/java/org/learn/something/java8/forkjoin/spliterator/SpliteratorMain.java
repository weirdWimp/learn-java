package org.learn.something.java8.forkjoin.spliterator;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author guo
 * @date 2021/5/29
 */
public class SpliteratorMain {

    public static void main(String[] args) {
        int[] nums = IntStream.rangeClosed(1, 40).toArray();
        System.out.println(nums.length);

        SimpleSpliterator spliterator = new SimpleSpliterator(nums, 0, nums.length);
        Stream<Integer> stream = StreamSupport.stream(spliterator, true);
        System.out.println(stream.reduce(Integer::sum));


        // .forEach(integer -> System.out.println(Thread.currentThread() + " " + integer));
    }
}
