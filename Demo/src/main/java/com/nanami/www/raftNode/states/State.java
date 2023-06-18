package com.nanami.www.raftNode.states;

import com.nanami.www.raftNode.Node;

import java.net.Socket;

public interface State {
    public String entry(String request, String data, Node node);
//    public String entry(String request, String data, Socket socket);
}
