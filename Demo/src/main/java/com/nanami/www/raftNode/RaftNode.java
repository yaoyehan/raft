package com.nanami.www.raftNode;

import com.nanami.www.DealMessage;
import com.nanami.www.KVServer;
import com.nanami.www.raftNode.network.Network;
import com.nanami.www.raftNode.states.Candidate;
import com.nanami.www.raftNode.states.Follower;
import com.nanami.www.raftNode.states.Leader;
import com.nanami.www.raftNode.states.State;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RaftNode {

    int localID;
    int term;
    String state;
    State STATE;
    Node me;
    private ExecutorService pool;
    private KVServer kvServer;

    public RaftNode(String address, int port){
        term = 0;
        this.me = new Node(address, port);
        setState("follower");
    }

    public static Map<Integer, Node> nodeMap = new HashMap();


    public void setState(String state){
        this.state = state;
        if("leader".equals(state)) {
            STATE = new Leader();
        } else if("candidate".equals(state)) {
            STATE = new Candidate(this);
        } else if("follower".equals(state)) {
            STATE = new Follower(this);
        }
    }

    private void start() {
        Thread serverThread = new Thread(()->{
            try {
                ServerSocket server = new ServerSocket(me.getPort());
                pool = Executors.newCachedThreadPool();
                kvServer = new KVServer();

                while (true) {

                    Socket socket = server.accept();
                    Thread thread = new Thread(new DealMessage(socket) {

                        @Override
                        public void dealMessage() {
                            String data = Network.receive(socket);
                            if(data == null){
                                return;
                            }
                            String[] strings = data.split(" ");
                            STATE.entry(strings[0], strings[1], nodeMap.get(Integer.parseInt(strings[2])));
                        }
                    });
//            thread.start();
                    pool.execute(thread);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    private void followerStart() {

    }

    private void candidateStart() {

    }

    private void leaderStart() {

    }

    public int getLocalID() {
        return localID;
    }
}
