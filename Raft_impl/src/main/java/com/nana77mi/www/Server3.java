package com.nana77mi.www;

import com.nana77mi.www.RaftNode.RaftNode;

public class Server3 {
    public static void main(String[] args) {
        new RaftNode("localhost", 6666, "3").start();
    }
}
