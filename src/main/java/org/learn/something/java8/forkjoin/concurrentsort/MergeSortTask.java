package org.learn.something.java8.forkjoin.concurrentsort;

import java.util.Arrays;
import java.util.concurrent.CountedCompleter;

/**
 * @author guo
 * @date 2021/5/30
 */

@SuppressWarnings({"unchecked", "rawtypes"})
public class MergeSortTask extends CountedCompleter<Void> {

    private Comparable[] data;

    // include
    private int start;

    // exclude
    private int end;

    private int middle;

    public MergeSortTask(CountedCompleter<?> completer, Comparable[] data, int start, int end) {
        super(completer);
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public void compute() {
        if ((end - start) >= 4) {
            middle = start + end >>> 1;

            // divide and conquer
            MergeSortTask leftTask = new MergeSortTask(this, data, start, middle);
            MergeSortTask rightTask = new MergeSortTask(this, data, middle, end);

            addToPendingCount(1);
            leftTask.fork();
            rightTask.fork();
        } else {
            Arrays.sort(data, start, end);
            tryComplete();
        }
    }

    @Override
    public void onCompletion(CountedCompleter<?> caller) {
        System.out.printf("caller " + ((MergeSortTask) caller).start + " " + ((MergeSortTask) caller).end + " start:%s, end:%s onCompletion\n", start, end);

        // 没有子任务的任务，不需要合并
        if (middle == 0) {
            return;
        }

        // 对两个由子任务进行排序的左右（有序）子集进行合并排序
        int length = end - start;
        Comparable[] temp = new Comparable[length];
        int i, j, index;
        i = start;
        j = middle;
        index = 0;

        while ((i < middle) && (j < end)) {
            if (data[i].compareTo(data[j]) <= 0) {
                temp[index] = data[i];
                i++;
            } else {
                temp[index] = data[j];
                j++;
            }
            index++;
        }

        // 如果左子序列还有剩余元素
        while (i < middle) {
            temp[index] = data[i];
            i++;
            index++;
        }

        // 如果右子序列还有剩余元素
        while (j < end) {
            temp[index] = data[j];
            j++;
            index++;
        }

        // 调整原序列的元素位置，使得 [start, end) 区间有序
        for (int k = 0; k < (end - start); k++) {
            data[start + k] = temp[k];
        }
    }
}
