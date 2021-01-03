package org.learn.something.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MainTest {

    public static void main(String[] args) {
        LimitedLogin limitedLogin = new LimitedLogin(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        IntStream.rangeClosed(1, 5)
                .forEach(i -> executorService.execute(limitedLogin::tryLogin));
        executorService.shutdown();
        System.out.println("try login after full:" + limitedLogin.tryLogin());
    }


}
