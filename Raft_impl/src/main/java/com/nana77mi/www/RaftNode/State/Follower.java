package com.nana77mi.www.RaftNode.State;

import com.nana77mi.www.RaftNode.RaftNode;
import com.nana77mi.www.RaftNode.TimeUtil.TimeUtil;

import java.net.Socket;

public class Follower implements State{

    private RaftNode raftNode;
    private TimeUtil timeUtil;
    private Thread timeMonitor;
    private TimeUtil voteCD;
    private volatile boolean run;
    private final int checkTime = 200;

    public Follower(RaftNode raftNode){
        this.raftNode = raftNode;
        timeUtil = new TimeUtil();
        voteCD = new TimeUtil(1L);
        timeMonitorStart();
    }

    private void timeMonitorStart() {
        run = true;
        timeMonitor = new Thread(()->{
            while (run) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (timeUtil){
                    if (timeUtil.isOvertime()) {
                        run = false;
                        raftNode.followerToCandidate();
                        break;
                    }
                }
            }
        });
        timeMonitor.start();
    }

    private void resetTimeUtil() {
        System.out.println("reset");
        synchronized (timeUtil){
            timeUtil.updateLastTime();
        }
        System.out.println("wait");
    }

    private void electionReply(String[] strings) {
        System.out.println("get an election");
        int index = Integer.parseInt(strings[2]);
        if(voteCD.isOvertime()) {
            if (raftNode.getIndex() <= index) {
                raftNode.vote(strings[3]);
                resetTimeUtil();
            } else {
                raftNode.refuse(strings[3]);
            }
            voteCD.updateLastTime();
        }
    }

    @Override
    public void dealMessage(String[] strings) {
        switch (strings[1]) {
            case "heartBeat":
            case "refuse":
                resetTimeUtil();
                break;
            case "election":
                electionReply(strings);
                break;
            default:
                break;
        }
    }

    @Override
    public void dealMessage(String[] strings, Socket socket) {

    }

    @Override
    public String currentState() {
        return "follower";
    }
}
