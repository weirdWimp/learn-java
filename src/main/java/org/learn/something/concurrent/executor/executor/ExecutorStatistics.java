package org.learn.something.concurrent.executor.executor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author guo
 * @date 2021/4/29
 */
public class ExecutorStatistics {

    private AtomicLong executionTime = new AtomicLong(0L);

    private AtomicInteger numTasks = new AtomicInteger(0);

    public void addExecutionTime(long time) {
        executionTime.addAndGet(time);
    }

    public void addTask() {
        numTasks.incrementAndGet();
    }

    @Override
    public String toString() {
        return "ExecutorStatistics{" +
                "executionTime=" + executionTime +
                ", numTasks=" + numTasks +
                '}';
    }
}
