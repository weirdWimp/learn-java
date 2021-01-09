package org.learn.something.concurrent.pattern.threadspecificstorage;

public interface Log {

    void log(String message);

    void close();

}
