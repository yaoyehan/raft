package com.nana77mi.www.RaftNode;

public class ServeStart {
    public static void main(String[] args) {
        new RaftNode("localhost", 7777, "server").start();

    }
}
