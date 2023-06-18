package com.nanami.www;

import com.nanami.www.raftNode.RaftNode;

public class Main {
    public static void main(String[] args) {
        RaftNode raftNode = new RaftNode("localhost", 1234);
    }
}
