package com.nana77mi.www.RaftNode;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Node {
    private boolean connect;
    private String host;
    private int port;

    public Node(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean equals(Node node) {
        return host.equals(node.host) && port == node.port;
    }

    public Socket getSocket(){
        try {
            return new Socket(host, port);
        } catch (IOException e) {

        }
        return null;
    }

    public DatagramPacket getDatagramPackage(String data) {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        try {
            InetAddress address = InetAddress.getByName(this.host);
            return new DatagramPacket(dataBytes, dataBytes.length, address, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
