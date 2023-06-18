package com.nana77mi.www.RaftNode;
import com.nana77mi.www.RaftNode.Network.Network;
import com.nana77mi.www.RaftNode.State.Candidate;
import com.nana77mi.www.RaftNode.State.Follower;
import com.nana77mi.www.RaftNode.State.Leader;
import com.nana77mi.www.RaftNode.State.State;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class RaftNode {

    private Network network;
    private Node me;
    private String name;
    private int term;
    private int index;
    private State state;
    private Map<String, Node> nodeMap;
    public RaftNode(String host, int port, String name) {
        me = new Node(host, port);
        this.name = name;
        index = 0;
        network = new Network(host, port, this);
        nodeMap = new ConcurrentHashMap<>();
        nodeMap.put("1", new Node("localhost", 4444));
        nodeMap.put("2", new Node("localhost", 5555));
        nodeMap.put("3", new Node("localhost", 6666));
        nodeMap.put("4", new Node("localhost", 7777));
        nodeMap.put("5", new Node("localhost", 8888));
        index = port;
    }

    public void start() {
        state = new Follower(this);
        network.startListening();
        System.out.println("start");
    }


    public void dealMessage(String[] strings) {
        state.dealMessage(strings);
    }

    private void broadcast(String message) {
        for(Map.Entry<String, Node> entry : nodeMap.entrySet()) {
            Node node = entry.getValue();
            Thread thread = new Thread(()->{
                if(!node.equals(me)){
                    Socket socket = node.getSocket();
                    if(socket == null){
                        return;
                    }
                    network.tcpSent(message, socket);
                }
            });
            thread.start();
        }
    }

    public void heartBeat() {
        broadcast("heartBeat");
        System.out.println("sent heartbeats");
    }

    public void election() {
        broadcast("election " + index + " " + name);
        System.out.println("start election");
    }

    public void vote(String name) {
        Socket socket = nodeMap.get(name).getSocket();
        if (socket == null) {
            return;
        }
        network.tcpSent("vote " + this.name, socket);
        System.out.println("vote for " + name);
    }

    public void refuse(String name) {
        Socket socket = nodeMap.get(name).getSocket();
        if(socket == null) {
            return;
        }
        network.tcpSent("refuse", socket);
        System.out.println("refuse " + name);
    }

    public synchronized void followerToCandidate() {
        if(state.currentState().equals("follower")){
            network.stopListening();
            state = new Candidate(this);
            System.out.println("followerToCandidate");
            network.startListening();
        }
    }

    public synchronized void CandidateToFollower() {
        if(state.currentState().equals("candidate")){
            network.stopListening();
            state = new Follower(this);
            System.out.println("candidateToFollower");
            network.startListening();
        }
    }

    public synchronized void CandidateToLeader() {
        if(state.currentState().equals("candidate")){
            network.stopListening();
            state = new Leader(this);
            System.out.println("CandidateToLeader");
            network.startListening();
        }
    }

    public int getIndex() {
        return index;
    }

    public int getGroupSize() {
        return nodeMap.size();
    }
}
