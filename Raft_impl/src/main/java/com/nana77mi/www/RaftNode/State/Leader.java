package com.nana77mi.www.RaftNode.State;

import com.nana77mi.www.RaftNode.HeartBeatTask;
import com.nana77mi.www.RaftNode.RaftNode;

import java.net.Socket;

public class Leader implements State{

    private HeartBeatTask heartBeatTask;

    public Leader(RaftNode raftNode) {
        heartBeatTask = new HeartBeatTask(raftNode);
        heartBeatTask.start();
    }

    @Override
    public void dealMessage(String[] strings) {

    }

    @Override
    public void dealMessage(String[] strings, Socket socket) {

    }

    @Override
    public String currentState() {
        return "leader";
    }
}
