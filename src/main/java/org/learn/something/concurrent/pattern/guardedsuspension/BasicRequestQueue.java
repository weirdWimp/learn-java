package org.learn.something.concurrent.pattern.guardedsuspension;

import java.util.LinkedList;
import java.util.Queue;

public class BasicRequestQueue {

    private Queue<Request> requests = new LinkedList<>();

    /**
     * guarded suspension（被守护而暂停执行），只有当守护条件成立时（在这里为 requests.peek() != null）, 才能继续执行，否则
     * 进行等待，直到实例的状态发生该变，并通知该线程，再次进行守护条件的判断；
     * <p>
     * 并且 {@link Object#wait()} 方法明确指出应当在循环中使用；
     * <p>
     * 线程使用 wait 进行等待期间值停止执行的，所以避免了对 Java 虚拟机的处理时间的浪费
     *
     * @return
     */
    public synchronized Request getRequest() {
        while (requests.peek() == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                // just ignore to launch a new while test
            }
        }
        return requests.remove();
    }

    public synchronized void putRequest(Request request) {
        requests.offer(request);
        notifyAll();
    }
}
