package org.learn.something.date;

import com.sun.istack.internal.NotNull;
import org.learn.something.extend.Circle;
import org.learn.something.extend.CircleSubClass;
import org.learn.something.extend.Eclipse;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DateTimeTest {

    public static void main(String[] args) {
        dateTest();
    }

    public static void dateTest() {
        final LocalDate now = LocalDate.now();
        final LocalDate after4Day = now.plusYears(1).plusDays(4);

        final long days = ChronoUnit.DAYS.between(now, after4Day);
        final Period period = now.until(after4Day);
        final int days1 = (int) period.getDays();
        System.out.println("ISO 8601 时间段表示法 = " + period);
        System.out.println("days1 = " + days1);
        System.out.println("days2 = " + days);

        List<Circle> list = new ArrayList<Circle>();
        Comparator<String> comparator = Comparator.comparing(String::length);


        ArrayList<? super Eclipse> list1 = new ArrayList<Circle>();
        ArrayList<? extends Circle> list2 = new ArrayList<Eclipse>();

        List<String> list3 = new ArrayList<>();
        System.out.println("list3 = " + list3.getClass());

        Function<Circle, Integer> circleFunc = circle -> circle.getR().length();

        // 当左边的类型为 CircleSubClass 时， keyGenerator 的负载类型可以是 CircleSubClass 的任何父类型
        // 当右边 keyGenerator 的负载类型是 Circle 时，左边的 Comparator 负载类型可以是 Circle 的任何子类
        Comparator<CircleSubClass> comparing = comparing(circleFunc);

    }

    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
    }

    public static <T, E extends Comparable<? super E>> Comparator<T> comparing(Function<? super T, ? extends E> keyGenerator) {
        Comparator<T> comparator1 = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                // 假设 keyGenerator 的定义接受一个 T 的父类型, 如 FatherT
                // 此时 o1 是 T 类型的，是可以作为 keyGenerator.apply(FatherT fatherT) 的参数的
                // 会进行向上转换，是合理有效的，任何父类引用都可以指向子类对象（父类的方法都会被子类继承，所以是合理的）
                return keyGenerator.apply(o1).compareTo(keyGenerator.apply(o2));
            }
        };

        // lambda 表达式写法
        Comparator<T> comparator = (c1, c2) -> keyGenerator.apply(c1).compareTo(keyGenerator.apply(c2));
        return comparator;
    }

    // ？
    public static int length(@NotNull String input) {
        return input.length();
    }
}
