package org.learn.something.java8;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

public class PrimeCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> {
            Map<Boolean, List<Integer>> acc = new HashMap<>();
            acc.put(true, new ArrayList<>());
            acc.put(false, new ArrayList<>());
            return acc;
        };
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer i) -> {
            acc.get(isPrime(acc.get(true), i)).add(i);
        };
    }

    /**
     * 不支持并行
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    private static boolean isPrime(List<Integer> previousPrimes, int candidate) {
        int root = (int) Math.sqrt(candidate);
        return takeWile(previousPrimes, i -> i <= root)
                .stream()
                .noneMatch(p -> candidate % p == 0);
    }

    private static <T> List<T> takeWile(List<T> items, Predicate<T> predicate) {
        for (int i = 0; i < items.size(); i++) {
            if (!predicate.test(items.get(i))) {
                // index i exclusive
                return items.subList(0, i);
            }
        }
        return items;
    }
}
