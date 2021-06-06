package org.learn.something.concurrent.executor.executor;

import org.learn.something.concurrent.executor.command.ConcurrentCommand;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author guo
 * @date 2021/4/29
 */
public class ServerExecutor extends ThreadPoolExecutor {

    private static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static int KEEP_ALIVE_TIME = 10;

    private ConcurrentHashMap<Runnable, Instant> startTimes;

    private ConcurrentHashMap<String, ExecutorStatistics> executorStatistics;

    public ServerExecutor() {
        // 优先级阻塞队列存储任务
        super(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new PriorityBlockingQueue<>());
        startTimes = new ConcurrentHashMap<>();
        executorStatistics = new ConcurrentHashMap<>();
    }

    /**
     * 每个任务执行之前执行，保存每个任务执行的开始时间, 由执行该任务的线程调用
     *
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTimes.put(r, Instant.now());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        ServerTask<?> task = (ServerTask<?>) r;
        ConcurrentCommand command = task.getCommand();

        if (t == null) {
            // 计算统计信息
            Instant start = startTimes.remove(r);
            ExecutorStatistics statistics = this.executorStatistics.computeIfAbsent(command.getUserName(), name -> new ExecutorStatistics());
            statistics.addExecutionTime(Instant.now().getEpochSecond() - start.getEpochSecond());
            statistics.addTask();

            // todo: 任务执行结束，从服务器保存的映射中一处该任务对应的 command
        } else {
            System.out.println(t);
        }

    }

    /**
     * submit(ConcurrentCommand)
     *
     * @param runnable
     * @param value
     * @param <T>
     * @return
     */
    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new ServerTask<T>(((ConcurrentCommand) runnable));
    }

    public void writeStatistics() {
        for (Map.Entry<String, ExecutorStatistics> entry : executorStatistics.entrySet()) {
            String user = entry.getKey();
            ExecutorStatistics statistics = entry.getValue();
            System.out.println(user + ":" + statistics);
        }
    }
}
