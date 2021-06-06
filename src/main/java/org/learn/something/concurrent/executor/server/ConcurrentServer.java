package org.learn.something.concurrent.executor.server;

import lombok.SneakyThrows;
import org.learn.something.concurrent.executor.command.ConcurrentCommand;
import org.learn.something.concurrent.executor.executor.ServerTask;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author guo
 * @date 2021/4/29
 */
public class ConcurrentServer {

    private static volatile boolean stopped = false;

    private static volatile LinkedBlockingQueue<Socket> pendingConnections = new LinkedBlockingQueue<>();

    // 用于保存 Future 实例（ServerTask）实现任务撤销
    private static volatile ConcurrentHashMap<String, ConcurrentHashMap<ConcurrentCommand, ServerTask<?>>> taskController = new ConcurrentHashMap<>();

    private static Thread requestThread;

    private static RequestTask task;

    private static ServerSocket serverSocket;

    @SneakyThrows
    public static void main(String[] args) {

        // 此线程负责将客户端socket转换成任务并提交给 Executor
        task = new RequestTask(pendingConnections, taskController);
        requestThread = new Thread(task);
        requestThread.start();

        serverSocket = new ServerSocket(8998);
        do {
            Socket clientSocket = serverSocket.accept();
            pendingConnections.put(clientSocket);
        } while (!stopped);

        // todo finish work
    }


}
