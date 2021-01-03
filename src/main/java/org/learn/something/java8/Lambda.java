package org.learn.something.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lambda {

    @FunctionalInterface
    interface Action {
        void act();
    }

    public static void main(String[] args) {
        Comparator<Object> objectComparator1 = (o1, o2) -> o1.hashCode() - o2.hashCode();
        Comparator<Object> objectComparator2 = (Object o1, Object o2) -> o1.hashCode() - o2.hashCode();

        List<String> strings = Arrays.asList("a1", "b2", "c3");
        mapPrint(strings, UnaryOperator.identity());

        execute((Action) () -> System.out.println("hello world"));

        int number = 6379;
        execute((Runnable) () -> System.out.println(number));

        mapPrint(strings, Integer::parseInt);

        methodRef("Hello", 1, String::substring);

        strings.sort(String::compareToIgnoreCase);

        String preDefined = "Hello World";

        BiPredicate<List<String>, String> contains = List::contains;

        Predicate<String> startsWith = preDefined::startsWith;

        Supplier<String> s1 = String::new;
        String s = s1.get();

        Function<String, Integer> s2 = Integer::new;
        Integer integer = s2.apply("10");

        IntStream.rangeClosed(0, 10).boxed().collect(Collectors.toCollection(ArrayList::new));

    }


    private static boolean isValidString(String string, Predicate<String>... predicates) {
        for (Predicate<String> predicate : predicates) {
            if (!predicate.test(string)) {
                return false;
            }
        }
        return true;
    }

    private static void complexCombination() {
        // f(x) = x + 1
        // y(x) = x * 2
        // g(x) = f(x) * 2 = ( x + 1) * 2

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> y = x -> x * 2;

        Function<Integer, Integer> g1 = f.andThen(y);
        Function<Integer, Integer> g2 = f.compose(y);
    }


    private static <T, U, R> void methodRef(T t, U u, BiFunction<T, U, R> function) {
        System.out.println(function.apply(t, u));
    }

    private static <T, R> void mapPrint(List<T> list, Function<T, R> function) {
        for (T t : list) {
            System.out.println(function.apply(t));
        }
    }

    private static <T> void consumePrint(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    private static void execute(Runnable runnable) {
        runnable.run();
    }

    private static void execute(Action action) {
        action.act();
    }

}
