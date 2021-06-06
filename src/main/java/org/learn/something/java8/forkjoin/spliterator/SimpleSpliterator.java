package org.learn.something.java8.forkjoin.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author guo
 * @date 2021/5/29
 */
public class SimpleSpliterator implements Spliterator<Integer> {

    private int[] nums;

    // include
    private int start;

    // exclude
    private int end;

    public SimpleSpliterator(int[] nums, int start, int end) {
        this.nums = nums;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Integer> action) {
        System.out.println(Thread.currentThread() + " consumes " + nums[start]);
        action.accept(nums[start++]);
        return start < end;
    }

    @Override
    public Spliterator<Integer> trySplit() {
        int currentSize = end - start;
        if (currentSize <= 10) {
            return null;
        }

        int splitPos = start + currentSize / 2;
        Spliterator<Integer> spliterator = new SimpleSpliterator(nums, start, splitPos);

        start = splitPos;
        return spliterator;
    }

    @Override
    public long estimateSize() {
        return end - start;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL;
    }
}
