package org.learn.something.concurrent.executor.server;

import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.learn.something.concurrent.executor.command.ConcurrentCommand;
import org.learn.something.concurrent.executor.command.ConcurrentQueryCommand;
import org.learn.something.concurrent.executor.executor.ServerExecutor;
import org.learn.something.concurrent.executor.executor.ServerTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author guo
 * @date 2021/4/29
 */
public class RequestTask implements Runnable {

    private LinkedBlockingQueue<Socket> pendingConnections;

    private ConcurrentHashMap<String, ConcurrentHashMap<ConcurrentCommand, ServerTask<?>>> taskController;

    private ServerExecutor serverExecutor = new ServerExecutor();

    public RequestTask(LinkedBlockingQueue<Socket> pendingConnections, ConcurrentHashMap<String, ConcurrentHashMap<ConcurrentCommand, ServerTask<?>>> taskController) {
        this.pendingConnections = pendingConnections;
        this.taskController = taskController;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Socket clientSocket = pendingConnections.take();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line = in.readLine();

            String[] params = line.split(";");

            ConcurrentQueryCommand queryCommand = new ConcurrentQueryCommand(params[0], params[1], Integer.parseInt(params[2]));

            // 提交任务
            ServerTask<?> future = (ServerTask<?>) serverExecutor.submit(queryCommand);

            taskController.computeIfAbsent(queryCommand.getUserName(), k -> new ConcurrentHashMap<>())
                    .put(queryCommand, future);
        }
    }
}
