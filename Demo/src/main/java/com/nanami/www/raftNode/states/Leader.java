package com.nanami.www.raftNode.states;

import com.nanami.www.raftNode.Node;
import com.nanami.www.raftNode.RaftNode;
import com.nanami.www.raftNode.network.Network;

import java.util.Map;

public class Leader implements State{

    public Leader() {
        new Thread(()->{
            while (true) {
                Leader.heartBeat();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 发送心跳
     */
    public static void heartBeat() {
        for(Map.Entry<Integer, Node> entry : RaftNode.nodeMap.entrySet()) {
            Node node = entry.getValue();
            Network.heartBeat(node, "");
        }
    }



    public String entry(String request, String data, Node node) {
        switch (request) {
            case "get":

                break;
            case "put":

                break;
            case "":
            default:
                return "IllegalRequest";
        }
        return "success";
    }

}
