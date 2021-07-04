package org.learn.something.algorithm.sort.select;

import java.util.Arrays;

/**
 * @author guo
 * @date 2021/7/1
 */
public class SelectSort {

    /**
     * 直接选择排序，对于 n 个数，需要进行 n-1 趟比较:
     * <p>
     * 第 1 趟比较，以第 1 个数作为基准，与剩下的 n - 1 进行比较，如果某个数比其小，就进行交换，也可以只记录最小数据的位置，最后才进行交换
     * 第 2 趟比较，以第 2 个数作为基准，与剩下的 n - 2 进行比较，如果某个数比其小，就进行交换，也可以只记录最小数据的位置，最后才进行交换
     * ...
     * 进行 n-1 趟后，前 n-1 个数是有序的，这个 n 个数自然也是有序的
     * <p>
     * 一共需要进行 (n-1) + (n-2) + (n-3) + ... + 1  = 1/2 * n^2 次内循环，时间复杂度: O(n^2)
     * 只需要一个附加单元用于交换 空间复杂度: O(1)
     * <p>
     * 排序是不稳定的：两个记录的关键字相等，但排序后的次序发生了变化
     *
     * @param data
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void sort(T[] data) {
        for (int i = 0; i < (data.length - 1); i++) {
            int index = i;

            // 找出本次最小数据的索引
            for (int j = i + 1; j < data.length; j++) {
                if (data[index].compareTo(data[j]) > 0) {
                    index = j;
                }
            }

            // 每趟最多交换一次，避免多次交换造成的性能损失
            if (index != i) {
                T temp = data[i];
                data[i] = data[index];
                data[index] = temp;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] integers = {20, 10, 9, 2, 15, 16, 50, 1};
        sort(integers);
        System.out.println(Arrays.toString(integers));
    }
}
