package com.nanami.www.raftNode;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Node {
    boolean connect;
    String address;
    int port;

    public Node(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public Socket getSocket(){
        try {
            return new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DatagramPacket getDatagramPackage(String data) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        try {
            InetAddress address = InetAddress.getByName(this.address);
            return new DatagramPacket(dataBytes, dataBytes.length, address, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPort() {
        return port;
    }
}
