package org.learn.something.concurrent.pattern.readwritelock;

public class SharedData {

    private final char[] buffer;

    private final ReadWriteLock readWriteLock = new ReadWriteLock();

    public SharedData(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = '*';
        }
    }

    public char[] read() throws InterruptedException {
        readWriteLock.readLock();
        try {
            return doRead();
        } finally {
            readWriteLock.readUnlock();
        }
    }

    public void write(char c) throws InterruptedException {
        readWriteLock.writeLock();
        try {
            doWrite(c);
        } finally {
            readWriteLock.writeUnlock();
        }
    }

    private char[] doRead() {
        char[] newBuffer = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            newBuffer[i] = buffer[i];
        }
        return newBuffer;
    }

    private void doWrite(char c) throws InterruptedException {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly();
        }
    }

    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
