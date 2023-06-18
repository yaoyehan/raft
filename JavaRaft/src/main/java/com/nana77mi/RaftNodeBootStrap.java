package com.nana77mi;


import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultNode;

import java.util.Arrays;

public class RaftNodeBootStrap {

    public static void main(String[] args) throws Throwable {
        main0();
    }

    public static void main0() throws Throwable {
        String[] peerAddr = {"localhost:8775", "localhost:8776", "localhost:8777", "localhost:8778", "localhost:8779"};

        NodeConfig config = new NodeConfig();

        // 自身节点
        config.setSelfPort(Integer.valueOf(System.getProperty("serverPort")));

        // 其他节点地址
        config.setPeerAddrs(Arrays.asList(peerAddr));

        Node node = DefaultNode.getInstance();
        node.setConfig(config);

        node.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                node.destroy();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }));
    }

    

}