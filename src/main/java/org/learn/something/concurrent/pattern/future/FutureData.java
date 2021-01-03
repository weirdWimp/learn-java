package org.learn.something.concurrent.pattern.future;

import java.util.concurrent.ExecutionException;

public class FutureData implements Data {

    private RealData realData;

    private boolean isReady;

    private ExecutionException exception;

    /**
     * Balking 模式：状态的值只允许被设置一次
     *
     * @param realData
     */
    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }

        this.realData = realData;
        isReady = true;
        notifyAll();
    }


    /**
     * Guarded Suspension 模式：如果未就绪，就继续等待
     *
     * @return
     */
    @Override
    public synchronized String getContent() throws ExecutionException {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                // ignore
            }
        }

        // 运行过程中如果由异常，直接抛出
        if (exception != null) {
            throw exception;
        }

        return realData.getContent();
    }

    /**
     * 包装异常，在实现异步任务的过程中的所有异常，捕获并调用此方法
     *
     * @param throwable
     */
    public synchronized void setException(Throwable throwable) {
        if (isReady) {
            return;
        }

        this.exception = new ExecutionException(throwable);
        this.isReady = true;
        notifyAll();
    }
}
