package org.learn.something.concurrent.pattern.future;

import java.util.concurrent.ExecutionException;

public interface Data {

    String getContent() throws ExecutionException;
}
