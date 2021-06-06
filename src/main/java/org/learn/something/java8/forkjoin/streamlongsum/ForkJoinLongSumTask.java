package org.learn.something.java8.forkjoin.streamlongsum;

import java.util.concurrent.RecursiveTask;

public class ForkJoinLongSumTask extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinLongSumTask(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinLongSumTask(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= 1000) {
            return computeSum();
        }

        // 异步执行子任务
        ForkJoinLongSumTask leftTask = new ForkJoinLongSumTask(numbers, start, start + length / 2);
        leftTask.fork();

        // 同步执行右边的子任务，有可能还要继续进行递归划分
        ForkJoinLongSumTask rightTask = new ForkJoinLongSumTask(numbers, start + length / 2, end);
        Long rightRes = rightTask.compute();

        // join 阻塞调用方，直到任务结果返回，调用之前，其他的子任务必须已经启动
        Long leftRes = leftTask.join();
        return leftRes + rightRes;
    }

    private long computeSum() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
