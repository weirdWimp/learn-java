package org.learn.something.concurrent.pattern.threadspecificstorage;

public class TSLogProxy {

    private static final ThreadLocal<TSLog> LOG_THREAD_LOCAL = new ThreadLocal<>();

    public static void log(String message) {
        getLog().log(message);
    }

    public static void close() {
        getLog().close();
    }

    private static TSLog getLog() {
        TSLog tsLog = LOG_THREAD_LOCAL.get();
        if (tsLog == null) {
            String name = Thread.currentThread().getName();
            tsLog = new TSLog(name + ".log");
            LOG_THREAD_LOCAL.set(tsLog);
        }
        return tsLog;
    }
}
