package org.learn.something.concurrent.pattern.guardedsuspension;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 使用  {@link LinkedBlockingQueue} 不需要使用 synchronized 进行同步，因为其内部对 take 方法和 put 方法已经使用了
 * {@link java.util.concurrent.locks.ReentrantLock} 进行加锁，并在内部使用了 Guarded Suspension 模式
 */
public class LinkedBlockingRequestQueue {

    private BlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    public Request getRequest() {
        Request request = null;
        while (request == null) {
            try {
                request = queue.take();
            } catch (InterruptedException e) {
                // ignore
            }
        }
        return request;
    }

    public void putRequest(Request request) {
        try {
            queue.put(request);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
