package com.nana77mi.www;

import com.nana77mi.www.RaftNode.RaftNode;

public class Server4 {
    public static void main(String[] args) {
        new RaftNode("localhost", 7777, "4").start();
    }
}
