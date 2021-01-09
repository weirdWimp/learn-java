package org.learn.something.concurrent.pattern.threadspecificstorage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TSLog implements Log {

    private PrintWriter printWriter;

    public TSLog(String fileName) {
        try {
            printWriter = new PrintWriter(new FileWriter(fileName), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(String message) {
        printWriter.println(String.format("[%-5s] - %s", Thread.currentThread().getName(), message));
    }

    @Override
    public void close() {
        printWriter.close();
    }
}
