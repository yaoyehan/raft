package com.nana77mi.www.RaftNode.State;

import java.net.Socket;

public interface State {
    public void dealMessage(String[] strings);
    public void dealMessage(String[] strings, Socket socket);
    public String currentState();

}
