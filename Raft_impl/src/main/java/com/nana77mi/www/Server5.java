package com.nana77mi.www;

import com.nana77mi.www.RaftNode.RaftNode;

public class Server5 {
    public static void main(String[] args) {
        new RaftNode("localhost", 8888, "5").start();
    }
}
