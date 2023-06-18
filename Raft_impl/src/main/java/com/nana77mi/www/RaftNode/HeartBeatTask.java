package com.nana77mi.www.RaftNode;

import com.nana77mi.www.RaftNode.Node;
import com.nana77mi.www.RaftNode.RaftNode;

import java.util.Map;

public class HeartBeatTask {

    private final int defaultTimeInterval = 1000;
    private Thread heartBeat;
    private RaftNode raftNode;
    private boolean run;

    public HeartBeatTask(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    public void start() {
        run = true;
        heartBeat = new Thread(()->{
            while (run) {
                raftNode.heartBeat();
                try {
                    Thread.sleep(defaultTimeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        heartBeat.start();
    }

    public void close() {
        run = false;
    }

}
