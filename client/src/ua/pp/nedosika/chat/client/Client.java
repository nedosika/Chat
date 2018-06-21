package ua.pp.nedosika.chat.client;

import ua.pp.nedosika.chat.network.TCPConnection;
import ua.pp.nedosika.chat.network.TCPConnectionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nedos on 21.06.2018.
 */
public class Client implements TCPConnectionListener{

    private TCPConnection connection;
    private BufferedReader keyboard;

    public static void main(String[] args) {
        new Client();
    }

    private Client(){
        try {
            connection = new TCPConnection(this, "127.0.0.1", 8189);
            keyboard = new BufferedReader( new InputStreamReader( System.in ) );

            String message = null;
            System.out.println("Наберите сообщение и нажмите \"Enter\"");

            while (true){
                message = keyboard.readLine();
                connection.sendMessage(message);
            }

        } catch (IOException e) {
            printMessage("Connection exception: " + e);
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connection Ready");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String message) {
        printMessage(message);
    }

    @Override
    public void onException(TCPConnection tcpConnection, IOException e) {
        System.out.println("Connection exception: " + e);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Connection close");
    }

    private synchronized void printMessage(String mesasge){
        System.out.println(mesasge);
    }
}
