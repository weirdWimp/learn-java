package org.learn.something.concurrent.pattern.termination;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrintTask extends Thread {

    private boolean isShutdown;

    public void shutdown() {
        if (isShutdown) {
            return;
        }
        isShutdown = true;
        interrupt();
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public void run() {
        try {
            while (!isShutdown) {
                System.out.println(Thread.currentThread().getName() + " 现在是 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
        } finally {
            doShutdown();
        }
    }

    public void doShutdown() {
        System.out.println(Thread.currentThread().getName() + " shutdown work done");
    }
}
