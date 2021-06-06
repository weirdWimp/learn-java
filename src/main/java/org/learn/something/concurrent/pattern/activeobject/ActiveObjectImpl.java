package org.learn.something.concurrent.pattern.activeobject;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ActiveObjectImpl implements ActiveObject {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public Future<String> mkString(char c, int count) {
        Callable<String> task = () -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < count; i++) {
                builder.append(c);
            }
            return builder.toString();
        };
        return executorService.submit(task);
    }

    @Override
    public void display(String charSequence) {
        Runnable task = () -> System.out.println("display: " + charSequence);
        executorService.execute(task);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }
}
