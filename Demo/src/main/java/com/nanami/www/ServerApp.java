package com.nanami.www;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {

        Server server = new Server(1234);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
