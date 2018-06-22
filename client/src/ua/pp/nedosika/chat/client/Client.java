package ua.pp.nedosika.chat.client;

import ua.pp.nedosika.chat.network.Message;
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
    private String login;

    public static void main(String[] args) {
        new Client();
    }

    private Client(){
        try {
            System.out.println("Введите Ваше имя:");
            keyboard = new BufferedReader( new InputStreamReader( System.in ) );
            login = keyboard.readLine();

            String message = null;
            connection = new TCPConnection(this, "127.0.0.1", 8189);

            while (true){
                message = keyboard.readLine();
                connection.sendMessage(new Message(login , message));
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connection Ready");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, Message message) {
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

    private synchronized void printMessage(Message message){
        System.out.println(message.getSender() +": "+ message.getMessage());
    }
}
