package com.nanami.www;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private String host;
    private int port;
    private Socket socket;


    public Client() throws IOException {
        this.host = "localhost";
        this.port = 1234;
        socket = new Socket(host, port);
    }

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
    }

    public void put(String key, String value) {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            String request = "put " + key + " " + value;
            dos.writeUTF(request);
//            System.out.println(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String request = "get " + key;
            dos.writeUTF(request);
//            System.out.println(request);
            return dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

}