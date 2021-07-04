package org.learn.something.algorithm.sort.exchange;

import java.util.Arrays;

/**
 * @author guo
 * @date 2021/7/4
 */
public class QuickSort {

    /**
     * 快速排序的思想：以某一个数为基准，使得该数左边的数都比该数小，右边的数都比该数大，
     * 然后对左边的序列和右边的序列递归应用此步骤
     *
     * @param data
     * @param start
     * @param end
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void sort(T[] data, int start, int end) {
        if (start >= end) {
            return;
        }

        T base = data[start];
        int i = start;
        int j = end + 1;

        while (true) {
            // 从最左边开始搜索，找到第一个大于基准值的索引
            // data[start+1]
            // data[start+2]
            // ...
            // data[end-1]
            // data[end]
            while (i < end && data[++i].compareTo(base) <= 0) ;

            // 从最右边开始搜索，找到第一个小于基准值的索引
            // data[end]
            // data[end-1]
            // ...
            // data[start+1]
            // data[start]
            while (j > start && data[--j].compareTo(base) >= 0) ;

            // 如果 i < j, 表明 i 的左边都是比基准值小的数，j 的右边都是比基准大的数，
            if (i < j) {
                swap(data, i, j);
            } else {
                // 两个方向的搜索已经覆盖了所有的元素，并且 j 索引处的元素比基准值小，交换基准值与 j 处的值
                // 就可以使得基准值左边的元素都比基准值小，基准值右边的值都比基准值大
                break;
            }
        }

        swap(data, start, j);
        sort(data, start, j - 1);
        sort(data, j + 1, end);
    }

    public static <T extends Comparable<? super T>> void swap(T[] data, int i, int j) {
        T temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] integers = {100, 23, 2, 29, 10, 3, 87, 25};
        System.out.println("before:" + Arrays.toString(integers));
        sort(integers, 0, integers.length - 1);
        System.out.println("after:" + Arrays.toString(integers));
    }

}
