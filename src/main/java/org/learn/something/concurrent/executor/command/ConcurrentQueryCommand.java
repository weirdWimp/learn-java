package org.learn.something.concurrent.executor.command;

/**
 * @author guo
 * @date 2021/4/29
 */
public class ConcurrentQueryCommand extends ConcurrentCommand {

    private final String userName;

    private final String instruct;

    private final int priority;


    public ConcurrentQueryCommand(String userName, String instruct, int priority) {
        this.userName = userName;
        this.instruct = instruct;
        this.priority = priority;
    }

    @Override
    public int compareTo(ConcurrentCommand o) {
        return 0;
    }

    @Override
    public void run() {

    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public String getInstruct() {
        return instruct;
    }
}
