package ua.pp.nedosika.chat.network;

import java.io.IOException;

/**
 * Created by nedos on 21.06.2018.
 */
public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpConnection);
    void onReceiveString(TCPConnection tcpConnection, String message);
    void onException(TCPConnection tcpConnection, IOException e);
    void onDisconnect(TCPConnection tcpConnection);

}
