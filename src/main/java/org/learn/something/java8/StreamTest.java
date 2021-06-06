package org.learn.something.java8;

import ch.qos.logback.classic.spi.TurboFilterList;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StreamTest {


    public static void main(String[] args) {
        Stream<String> stream = Stream.of("i", " love", "you");
        Stream<String> mapStream = stream.map(s -> {
            System.out.println(s);
            return s + "=";
        });
        mapStream.findFirst();
        int[] ints = new SplittableRandom().ints().limit(10).toArray();
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }


        System.out.println(IntStream.rangeClosed(1, 100000).parallel().reduce(Integer::sum));
        System.out.println(IntStream.rangeClosed(1, 100000).parallel().sum());


        // List<String> collectResult = IntStream.rangeClosed(1, 20)
        //         .mapToObj(String::valueOf)
        //         .parallel()
        //         .collect(
        //                 Collector.of(
        //                         () -> {
        //                             ArrayList<String> strings = new ArrayList<>();
        //                             System.out.println(Thread.currentThread() + " create supplier ");
        //                             return strings;
        //                         }, (l, e) -> {
        //                             System.out.println(Thread.currentThread() + " accumulate " + e);
        //                             l.add(e);
        //                         }, (left, right) -> {
        //                             System.out.println(Thread.currentThread() + " combine");
        //                             left.addAll(right);
        //                             return left;
        //                         }));

        // CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();

        // , Collector.Characteristics.IDENTITY_FINISH,
        //                 Collector.Characteristics.CONCURRENT,
        //                 Collector.Characteristics.UNORDERED

        // while (true) {
        List<String> collectResult = IntStream.rangeClosed(1, 20)
                .parallel()
                .mapToObj(String::valueOf)
                .collect(
                        Collector.of(
                                () -> {
                                    CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();
                                    System.out.println(Thread.currentThread() + " create supplier ");
                                    return strings;
                                }, (l, e) -> {
                                    System.out.println(Thread.currentThread() + " accumulate " + e);
                                    l.add(e);
                                }, (left, right) -> {
                                    System.out.println(Thread.currentThread() + " combine");
                                    left.addAll(right);
                                    return left;
                                }, Collector.Characteristics.IDENTITY_FINISH,
                                Collector.Characteristics.CONCURRENT,
                                Collector.Characteristics.UNORDERED));

        //     if (collectResult.stream().anyMatch(Objects::isNull)) {
        //         System.err.println("ERROR Null Element");
        //         break;
        //     }
        // }

        // CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<>();
    }

    private static void collect() {
        Map<Boolean, List<Integer>> partitionByIsPrime = partitionByIsPrime(100);
        partitionByIsPrime.get(true).forEach(System.out::println);
    }

    private static Map<Boolean, List<Integer>> partitionByIsPrime(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(StreamTest::isPrime));
    }

    private static boolean isPrime(int number) {
        int root = (int) Math.sqrt(number);
        return IntStream.rangeClosed(2, root).noneMatch(i -> number % i == 0);
    }

    private static boolean isPrimeBase(int number) {
        return IntStream.range(2, number).noneMatch(i -> number % i == 0);
    }
}
