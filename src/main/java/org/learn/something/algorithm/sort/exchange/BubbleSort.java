package org.learn.something.algorithm.sort.exchange;

import java.util.Arrays;

/**
 * @author guo
 * @date 2021/7/3
 */
public class BubbleSort {

    public static <T extends Comparable<? super T>> void sort(T[] data) {
        // 需要进行 n - 1 趟比较
        for (int i = 0; i < data.length - 1; i++) {
            boolean isSwap = false;
            for (int j = 0; j < data.length - 1 - i; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                    isSwap = true;
                }
            }
            if (!isSwap) {
                break;
            }
        }
    }

    public static <T extends Comparable<? super T>> void swap(T[] data, int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] integers = {100, 23, 2, 29, 10, 3, 87, 25};
        System.out.println("before:" + Arrays.toString(integers));
        sort(integers);
        System.out.println("after:" + Arrays.toString(integers));
    }

}
