package org.learn.something;

import com.sun.istack.internal.NotNull;
import org.learn.something.extend.BurritoOptionalExtra;
import org.learn.something.extend.Circle;
import org.learn.something.extend.Eclipse;
import org.learn.something.extend.Guacamole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainTest {

    public static interface Holder {
        int getValue();
    }

    private static interface ArrayMaker<T> {
        T[] apply(int i);
    }

    private static <T, R> List<R> opList(List<T> list, Function<T, R> opFunc){
        return list.stream().map(opFunc).collect(Collectors.toList());
    }

    private static Integer calSquareSize(String string, Function<String, Integer> function) {
        return function.apply(string);
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(MainTest.class);
        logger.info("start application...");

        // 1. cal length of input Sting; 2. return length + 1
        calSquareSize("Hello World", ((Function<Integer, Integer>) (length -> length + 1)).compose(String::length));


        Circle circle2 = new Circle();

        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        Iterator<String> iterator = list1.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equals("1")) {
                iterator.remove();
            }
            System.out.println("next = " + next);
        }


        String[] strings = new String[]{"a", "A", "b", "C", "B"};
        List<String> list = Arrays.asList(strings);
        List<String> integerList = opList(list, String::toString);

        Arrays.sort(strings, String::compareToIgnoreCase);

        ArrayMaker<String> arrayMaker = String[]::new;
        String[] apply = arrayMaker.apply(10);
        System.out.println("apply = " + apply.length);



        Holder[] holders = new Holder[10];
        for (int i = 0; i < holders.length; i++) {
            final int fi = i;

            /**
             * 局部类，能否访问 final 的局部变量，创建实例时，会创建该局部变量的副本
             * 在脱离定义这个局部类的作用域时，依然可以使用，也称之为闭包，保存了作用域的状态，
             * 并让这个作用域可以在后续的状态中继续使用
             */
            class MyHolder implements Holder {
                @Override
                public int getValue() {
                    return fi;
                }
            }

            holders[i] = new MyHolder();
        }


        for (Holder holder : holders) {
            System.out.println("holder.getValue() = " + holder.getValue());
        }


        Circle.StaticInnerClass innerClass = new Circle.StaticInnerClass();
        Eclipse eclipse = new Eclipse();
        Circle circle = new Circle();

        Circle[] circles = new Circle[10];
        circles[0] = eclipse;
        circles[1] = circle;

        for (Circle circle1 : circles) {
            if (circle1 != null) {
                circle1.description();
            }
        }

        Object arrayObj = new int[10];

        if (arrayObj instanceof int[]) {
            int[] integers = (int[]) arrayObj;
            System.out.println("integers = " + integers.length);
        }


        ArrayList<Circle> circles1 = new ArrayList<>();
        circles1.add(new Circle("1"));
        circles1.add(new Circle("2"));


        circles1.sort(Comparator.comparing(Object::hashCode).reversed());

        for (Circle circle1 : circles1) {
            System.out.println("circle1 = " + circle1.hashCode());
        }


        List<? super Eclipse> objects = circles1;

        for (Object object : objects) {
            System.out.println("object.getClass().getName() = " + object.getClass().getName());
        }

        Function<String, Integer> function1 = String::length;

        List<? extends Circle> circles2 = new ArrayList<Eclipse>();

        @NotNull String str = null;


        Circle circle1 = circles2.get(0);


        List<Eclipse> eclipseList = new ArrayList<>();
        sort(eclipseList);

        List<? super Eclipse> circles3 = new ArrayList<Circle>();
        Object object = circles3.get(0);

        List<String> map = map(eclipseList, Circle::getR);


    }


    private static void sort(List<? extends Circle> circles) {
        Circle circle = circles.get(0);
    }

    private static <T, U> List<U> map(List<T> list, Function<? super T, ? extends U> mapFunc) {
        List<U> resultList = new ArrayList<>(list.size());
        for (T t : list) {
            resultList.add(mapFunc.apply(t));
        }
        return resultList;
    }
}
