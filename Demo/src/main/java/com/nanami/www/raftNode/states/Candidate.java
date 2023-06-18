package com.nanami.www.raftNode.states;

import com.nanami.www.raftNode.Node;
import com.nanami.www.raftNode.RaftNode;
import com.nanami.www.raftNode.network.Network;

import java.util.Map;

public class Candidate implements State{

    RaftNode raftNode;
    private int voteCount;

    public Candidate(RaftNode raftNode) {
        this.voteCount = 0;
        this.raftNode = raftNode;
    }

    public void voteRequest() {
        for(Map.Entry<Integer, Node> entry : RaftNode.nodeMap.entrySet()){
            Network.sent("voteRequest", String.valueOf(voteCount), entry.getValue().getSocket());
        }
    }

    public void beLeader() {
        raftNode.setState("leader");
    }

    public void beFollower() {
        raftNode.setState("follower");
    }

    public String entry(String request, String data, Node node) {
        switch (request) {
            case "vote":
                this.voteCount++;
                if(voteCount > RaftNode.nodeMap.size()) {
                    beLeader();
                }
                break;
            case "voteRequest":

                break;
            case "":
                break;
            default:
                return "IllegalRequest";
        }
        return "success";
    }

}
