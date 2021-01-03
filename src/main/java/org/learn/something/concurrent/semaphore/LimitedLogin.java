package org.learn.something.concurrent.semaphore;

import java.util.concurrent.Semaphore;

public class LimitedLogin {

    private Semaphore semaphore;

    public LimitedLogin(int slot) {
        this.semaphore = new Semaphore(slot);
    }

    public boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    public void logout() {
        semaphore.release();
    }

    public int availableSlots() {
        return semaphore.availablePermits();
    }
}
