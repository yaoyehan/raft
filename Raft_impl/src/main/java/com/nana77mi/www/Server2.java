package com.nana77mi.www;

import com.nana77mi.www.RaftNode.RaftNode;

public class Server2 {
    public static void main(String[] args) {
        new RaftNode("localhost", 5555, "2").start();
    }
}
