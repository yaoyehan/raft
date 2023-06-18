package com.nanami.www;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int port;
    private ExecutorService pool;
    private KVServer kvServer;

    public Server() {
        this.port = 1234;
    }
    
    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {

        ServerSocket server = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        kvServer = new KVServer();

        while (true) {

            Socket socket = server.accept();
            Thread thread = new Thread(new DealMessage(socket) {

                @Override
                public void dealMessage() {
                    try{
                        while (true) {
                            String[] strings = dis.readUTF().split(" ");
                            if ("put".equals(strings[0])) {
                                kvServer.put(strings[1], strings[2]);
//                            System.out.println("put " + strings[1] + ":" + strings[2]);
                            } else if ("get".equals(strings[0])) {
                                dos.writeUTF(kvServer.get(strings[1]));
//                            System.out.println("get " + strings[1]);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
//            thread.start();
            pool.execute(thread);

        }

    }

    public void close() { pool.shutdown(); }
    
}
