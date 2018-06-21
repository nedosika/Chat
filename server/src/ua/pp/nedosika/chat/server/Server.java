package ua.pp.nedosika.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import ua.pp.nedosika.chat.network.TCPConnection;
import ua.pp.nedosika.chat.network.TCPConnectionListener;

/**
 * Created by nedos on 21.06.2018.
 */
public class Server implements TCPConnectionListener{
    public static void main(String[] args) {
        new Server();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private Server(){
        System.out.println("Server starting...");
        try (ServerSocket serverSocket = new ServerSocket(8189)){
            while (true){
                try {
                    new TCPConnection(this, serverSocket.accept());
                }
                catch (IOException e){
                    System.out.println("TCPConnection exception: " + e);
                }
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);

        }
    }

    @Override
    public synchronized void onConnectionReady(ua.pp.nedosika.chat.network.TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnection("Client connection: " + tcpConnection);
    }

    @Override
    public synchronized void onReceiveString(ua.pp.nedosika.chat.network.TCPConnection tcpConnection, String message) {
        sendToAllConnection(message);
    }

    @Override
    public synchronized void onException(ua.pp.nedosika.chat.network.TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exception: " + e);
    }

    @Override
    public synchronized void onDisconnect(ua.pp.nedosika.chat.network.TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnection("Client disconnected: " + tcpConnection);
    }

    private void sendToAllConnection(String message){
        System.out.println(message);

        final int cnt = connections.size();
        for (int i = 0; i < cnt; i++) {
            connections.get(i).sendMessage(message);
        }
    }
}
