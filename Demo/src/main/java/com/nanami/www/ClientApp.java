package com.nanami.www;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        for(int i = 0; i < 10; ++i){
            Client client;
            try {
                client = new Client("localhost", 1234);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            client.put("test" + i, i + "success");
            System.out.println(client.get("test" + i));
        }
    }
}
