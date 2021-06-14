package org.learn.something.java8.concurrent.datastrcture;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author guo
 * @date 2021/6/6
 */
public class ConcurrentMapTest {

    public static void main(String[] args) {
        // LongAdder 原子型变量
        ConcurrentHashMap<String, LongAdder> map = new ConcurrentHashMap<>();

        IntStream.rangeClosed(1, 100).parallel().forEach(i -> {
            String key = (i % 2 == 0) ? "even" : "odd";
            System.out.println(Thread.currentThread().getName() + " " + i + " " + key);
            map.computeIfAbsent(key, k -> {
                System.out.println(Thread.currentThread().getName() + " " + i + " create LongAdder");
                return new LongAdder();
            }).increment();
        });
        
        map.forEach((k, v) -> System.out.println(k + " " + v));
    }
}
