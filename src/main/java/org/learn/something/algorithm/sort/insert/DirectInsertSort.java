package org.learn.something.algorithm.sort.insert;

import java.util.Arrays;

/**
 * @author guo
 * @date 2021/7/4
 */
public class DirectInsertSort {

    /**
     * 直接插入排序：将元素插入到已经有序的序列中
     * <p>
     * 初始化有序序列包含第一个元素
     * 第 1 趟，将第 2 个元素插入到有序子序列中
     * 第 2 趟，将第 3 个元素插入到有序子序列中
     * ...
     * 第 n-1 趟，将第 n 个元素插入到有序子序列中
     *
     * @param data
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void sort(T[] data) {
        for (int i = 1; i < data.length; i++) {

            if (data[i].compareTo(data[i - 1]) >= 0) {
                continue;
            }

            int j = i - 1;
            T value = data[i];
            
            // 后移
            while (j > 0 && data[j].compareTo(value) > 0) {
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = value;
        }
    }

    public static void main(String[] args) {
        Integer[] integers = {100, 23, 2, 29, 10, 3, 87, 25};
        System.out.println("before:" + Arrays.toString(integers));
        sort(integers);
        System.out.println("after:" + Arrays.toString(integers));
    }
}
