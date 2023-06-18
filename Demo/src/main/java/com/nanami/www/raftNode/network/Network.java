package com.nanami.www.raftNode.network;

import com.nanami.www.raftNode.Node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class Network {

    /**
     * 发送请求
     */
    public static void sent(String request, String content, Socket socket) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(content.equals("") ? request : (request + " " + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 响应客户端
     */
    public static void reply(String data, Socket socket) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收信息
     */
    public static String receive(Socket socket){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            return dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sentAndReceive(String request, String content, Socket socket) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF(content.equals("") ? request : (request + " " + content));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送心跳
     */
    public static void heartBeat(Node node, String info) {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.send(node.getDatagramPackage(info));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
