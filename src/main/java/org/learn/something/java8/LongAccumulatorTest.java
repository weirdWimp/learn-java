package org.learn.something.java8;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Spliterators;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.LongStream;

/**
 * @author guo
 * @date 2021/6/8
 */
public class LongAccumulatorTest {

    public static void main(String[] args) {
        LongAccumulator longAccumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);
        LongStream.rangeClosed(1L, 100L).parallel().forEach(longAccumulator::accumulate);
        System.out.println(longAccumulator.get());

        ArrayList<String> strings = Lists.newArrayList("hello", "world", "hi", "i", "finish", "process");
        strings.parallelStream().forEach(s -> System.out.println(Thread.currentThread() + " " + s));
    }

}
