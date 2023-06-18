package com.nanami.www;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class DealMessage implements Runnable{

    private Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;

    public DealMessage(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public abstract void dealMessage() throws IOException, InterruptedException;
    // message 12
    @Override
    public void run() {
        try {
            dealMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}