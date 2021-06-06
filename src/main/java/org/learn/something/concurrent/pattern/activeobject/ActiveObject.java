package org.learn.something.concurrent.pattern.activeobject;

import java.util.concurrent.Future;

/**
 * 调用与执行分离，相互独立
 * Client(进行委托，请求调用)，Servant（执行处理），当 Servant 地处理时间很长或被推迟时，不希望 Client 角色受到影响
 * <p>
 * 1. 接收来自外部的异步请求，mkString，display 可以被多个线程调用
 * 2. 能够自由地调度请求
 * 3. 可以单线程执行实际地处理
 * 4. 可以返回直径结果 Future 模式
 * 5. 拥有独立地线程，Executors.newSingleThreadExecutor
 */
public interface ActiveObject {

    Future<String> mkString(char c, int count);

    void display(String charSequence);

    void shutdown();
}
