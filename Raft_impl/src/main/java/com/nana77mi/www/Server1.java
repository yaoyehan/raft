package com.nana77mi.www;

import com.nana77mi.www.RaftNode.RaftNode;

public class Server1 {
    public static void main(String[] args) {
        new RaftNode("localhost", 4444, "1").start();
    }
}
