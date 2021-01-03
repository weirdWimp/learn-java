package org.learn.something.concurrent;

import java.io.File;

public class ConcurrentTest {

    private static class LazyHolder {
        private static final String str;
        static {
            str = "";
            System.out.println("static inner class init");
        }
    }

    public static void main(String[] args) {
        final int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("processors = " + processors);
        // joinTest();
        // daemonTest();
        // exceptionHandlerTest();
        // searchFile();
        final String str = LazyHolder.str;
        System.out.println("main: " + str);
    }

    public static void joinTest() {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            final Thread thread = new Thread(() -> System.out.println("i = " + finalI));
            thread.start();
            try {
                if (i == 4) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void daemonTest() {
        final Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("true = " + true);
            }
        });
        thread.setName("local-daemon-thread-20200204");
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("main thread = " + i);
        }
    }

    /**
     * 线程因异常而退出时，默认的行为是打印线程的名称，异常类型，异常消息、堆栈打印
     */
    public static void exceptionHandlerTest() {
        final Thread thread = new Thread(() -> {
            throw new IllegalArgumentException("illegal argument");
        });

        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.printf("thread id: %s, thread name: %s, exception: %s", t.getId(), t.getName(), e);
        });
        thread.start();

        final Thread mainThread = Thread.currentThread();
        System.out.println("Main Thread = " + mainThread);
    }

    public static void searchFile() {
        final File file = new File("D:\\");
        final File[] files = file.listFiles();
        for (File file1 : files) {
            System.out.println("file1 = " + file1);
        }
    }
}
