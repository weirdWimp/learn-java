package org.learn.something.java8;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

public class CollectionTest {

    public static void main(String[] args) {
        mapForEach();
    }

    private static <E> void replaceAll(List<E> list, UnaryOperator<E> unaryOperator) {
        list.replaceAll(unaryOperator);
    }

    private static <E> void replaceAll(List<E> list) {
        ListIterator<E> listIterator = list.listIterator();
        if (listIterator.hasNext()) {
            E e = listIterator.next();
            listIterator.set(e);
        }
    }

    private static void mapForEach() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Z", 2);
        hashMap.put("D", 6);
        hashMap.put("A", 1);
        hashMap.put("C", 7);
        hashMap.put("B", 1);

        hashMap.forEach((string, integer) -> System.out.println(string + integer));

        System.out.println("\n# Key 排序");
        hashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);


        System.out.println("\n# value 排序");
        hashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(System.out::println);

        System.out.println("\n# 计算模式");
        String[] names = {"PengHui", "Tom", "Bob", "Jason", "Jerry", "James"};
        Map<Integer, List<String>> map = new HashMap<>();

        for (String name : names) {
            int length = name.length();
            map.computeIfAbsent(length, ArrayList::new).add(name);
        }

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);

        System.out.println("\n# 每个映射的值（List）中只保留一个元素");
        map.replaceAll((key, value) -> value.subList(0, 1));
        System.out.println(map);

        // 接受一个谓词，根据谓词的返回删除元素
        System.out.println("\n# 移除 Map 中 Key 小于 3 的映射");
        map.entrySet().removeIf(entry -> entry.getKey() > 3);
        System.out.println(map);

        ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.reduceValues(1, Long::max);
    }


}
