package org.learn.something.java8;

import java.util.List;
import java.util.Map;
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
