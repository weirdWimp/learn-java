package org.learn.something.concurrent.executor.executor;

import org.learn.something.concurrent.executor.command.ConcurrentCommand;

import java.util.concurrent.FutureTask;

/**
 * @author guo
 * @date 2021/4/29
 */
public class ServerTask<V> extends FutureTask<V> implements Comparable<ServerTask<V>> {

    private ConcurrentCommand command;

    public ServerTask(ConcurrentCommand command) {
        super(command, null);
        this.command = command;
    }

    @Override
    public int compareTo(ServerTask<V> o) {
        return this.command.compareTo(o.getCommand());
    }

    public ConcurrentCommand getCommand() {
        return command;
    }

    public void setCommand(ConcurrentCommand command) {
        this.command = command;
    }
}
