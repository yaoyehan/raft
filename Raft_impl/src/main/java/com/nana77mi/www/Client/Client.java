package com.nana77mi.www.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 7777);
            DataOutputStream dos= new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("hhh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
