package ua.pp.nedosika.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import ua.pp.nedosika.chat.network.*;

/**
 * Created by nedos on 21.06.2018.
 */
public class Server implements TCPConnectionListener{
    public static void main(String[] args) {
        new Server();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private static History chatHistory = new History();

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
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        System.out.println("Client connection: " + tcpConnection);

        for(Message message : chatHistory.getHistory()){
            tcpConnection.sendMessage(message);
        }
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, Message message) {
        sendToAllConnection(message);
        chatHistory.addMessage(message);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, IOException e) {
        System.out.println("TCPConnection exception: " + e);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        System.out.println("Client disconnected: " + tcpConnection);
        //sendToAllConnection(new Message(new User("Server"), "Client disconnected: " + tcpConnection));
    }

    private void sendToAllConnection(Message message){
        System.out.println(message.getDate() + " [" + message.getSender().getName() + "] " + message.getMessage());

        for (TCPConnection connection: connections) {
            connection.sendMessage(message);
        }
    }
}
