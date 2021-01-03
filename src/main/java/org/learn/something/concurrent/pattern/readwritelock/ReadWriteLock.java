package org.learn.something.concurrent.pattern.readwritelock;

/**
 * 读写锁实现
 *
 * @see java.util.concurrent.locks.ReadWriteLock
 * @see java.util.concurrent.locks.ReentrantReadWriteLock
 */
public class ReadWriteLock {

    private int readingThreads;
    private int writingThreads;

    /**
     * 避免写线程一直没有机会写：当读线程获取读锁后，如果这时有写线程因为获取写锁而处于等待的状态；如果在读线程释放读锁时，有新的读线程试图获取
     * 读锁时，是否要授予该读线程读锁，还是授予等待的写线程写锁？ 如果授予该读线程读锁，就会使得写线程可能一直处于无法更新的等待状态（后续会有源源不断的读线程进来）
     * <p>
     * 写线程释放写锁时，可以保证授予等待的读线程读锁和等待的写线程写锁是随机的
     */
    private int writeWaitThreads;
    private boolean preferredWrite;

    public synchronized void readLock() throws InterruptedException {
        while (writingThreads > 0 || (preferredWrite && writeWaitThreads > 0)) {
            wait();
        }
        readingThreads++;
    }

    public synchronized void readUnlock() {
        readingThreads--;
        preferredWrite = true;
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        writeWaitThreads++;
        try {
            while (readingThreads > 0 || writingThreads > 0) {
                wait();
            }
        } finally {
            writeWaitThreads--;
        }
        writingThreads++;
    }

    public synchronized void writeUnlock() {
        writingThreads--;
        preferredWrite = false;
        notifyAll();
    }
}
