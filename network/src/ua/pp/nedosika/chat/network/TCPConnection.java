package ua.pp.nedosika.chat.network;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.io.*;
import java.net.Socket;

/**
 * Created by nedos on 21.06.2018.
 */
public class TCPConnection {
    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public TCPConnection(TCPConnectionListener eventListener, String ipAddress, int port) throws IOException{
        this(eventListener, new Socket(ipAddress, port));
    }

    public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException{

        this.eventListener = eventListener;
        this.socket = socket;

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    eventListener.onConnectionReady(TCPConnection.this);

                    while(!rxThread.isInterrupted()){
                        Message message = (Message) in.readObject();
                        eventListener.onReceiveString(TCPConnection.this, message);
                    }
               }
                catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                } finally {
                    eventListener.onDisconnect(TCPConnection.this);
                }
            }
        });
        rxThread.start();
    }

    public synchronized void  sendMessage(Message message){
        try {
            out.writeObject(message);
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e );
            disconnect();
        }
    }

    public synchronized void disconnect(){
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this, e );
        }
    }

    @Override
    public String toString() {
        return "TCPConnection " + socket.getInetAddress() + ": " + socket.getPort();
    }

}
