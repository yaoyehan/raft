package com.nana77mi.www.RaftNode.Network;

import com.nana77mi.www.RaftNode.RaftNode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 封装网络IO
 */

public class Network {

    private String host;
    private int port;
    private ServerSocket server;
    private Map<String, Socket> socketMap;
    private RaftNode raftNode;
    private ExecutorService pool;
    private Thread serverThread;
    private boolean run;
    private Object lock;

    public Network(String host, int port, RaftNode node) {
        this.host = host;
        this.port = port;
        this.raftNode = node;
        lock = new Object();
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tcpSent(String message, Socket socket) {
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("server " + message);
            socket.close();
        } catch (IOException e) {

        }
    }

    private void tcpReceive() {
        serverThread = new Thread(()->{
            try {
                pool = Executors.newCachedThreadPool();
                while (run) {
                    Socket socket = server.accept();
                    Thread thread = new Thread(()->{
                        dealRequest(socket);
                    });
//            thread.start();
                    pool.execute(thread);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    private void dealRequest(Socket socket) {
        synchronized (lock){
            DataInputStream dis;
            try {
                dis = new DataInputStream(socket.getInputStream());
                String message = dis.readUTF();
                System.out.println("...GET MESSAGE: " + message);
                if (!"".equals(message)) {
                    String[] strings = message.split(" ");
                    if ("server".equals(strings[0])) {
                        raftNode.dealMessage(strings);
                    } else if ("client".equals(strings[0])) {
                        raftNode.dealMessage(strings, socket);
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startListening() {
        run = true;
        tcpReceive();
    }

    public void stopListening() {
        run = false;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            pool.shutdown();
        } catch (Exception e) {

        }
    }

}
