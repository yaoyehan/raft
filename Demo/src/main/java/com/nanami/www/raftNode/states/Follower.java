package com.nanami.www.raftNode.states;

import com.nanami.www.raftNode.Node;
import com.nanami.www.raftNode.RaftNode;
import com.nanami.www.raftNode.network.Network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

public class Follower implements State{

    static RaftNode raftNode;
    Timer timer;

    public Follower(RaftNode raftNode) {
        Follower.raftNode = raftNode;
        timer = new Timer();
        waitToCandidate();
    }

    /**
     * 重置计时器
     */
    private void waitToCandidate() {
        timer.cancel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                beCandidate();
            }
        }, 500, 1000);
    }

//    public String receiveHeartBeat(Node me) {
//        try {
//            // 1.创建服务器端DatagramSocket，指定端口
//            DatagramSocket socket = new DatagramSocket(me.getPort());
//            // 2.创建数据报，用于接收客户端发送的数据
//            byte[] data = new byte[1024];// 创建字节数组，指定接收的数据包的大小
//            DatagramPacket packet = new DatagramPacket(data, data.length);
//            // 3.接收客户端发送的数据
//            socket.receive(packet);// 此方法在接收到数据报之前会一直阻塞
//            // 4.读取数据
//            String info = new String(data, 0, packet.getLength());
//            return info;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private void vote(Node candidate) {
        Network.sent("vote", "agree", candidate.getSocket());
    }

    private void beCandidate() {
        raftNode.setState("candidate");
    }

    public String entry(String request, String data, Node node) {
        switch (request) {
            case "voteRequest":
                vote(node);
                break;
            case "heartbeat":
                waitToCandidate();
                break;
            default:
                return "IllegalRequest";
        }
        return "success";
    }

}
