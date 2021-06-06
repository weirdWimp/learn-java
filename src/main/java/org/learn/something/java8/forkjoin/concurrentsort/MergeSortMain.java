package org.learn.something.java8.forkjoin.concurrentsort;

import java.util.concurrent.ForkJoinPool;

/**
 * @author guo
 * @date 2021/5/30
 */
public class MergeSortMain {

    public static void main(String[] args) {
        Integer[] nums = {10, 2, 5, 100, 1, 23, 30, 230, 15, 12, 23, 121, 534, 55, 34};
        for (Integer num : nums) {
            System.out.println(num);
        }


        MergeSortTask mergeSortTask = new MergeSortTask(null, nums, 0, nums.length);
        ForkJoinPool.commonPool().invoke(mergeSortTask);

        for (Integer num : nums) {
            System.out.println(num);
        }
    }
}
