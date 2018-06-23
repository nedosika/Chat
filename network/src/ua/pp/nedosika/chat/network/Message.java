package ua.pp.nedosika.chat.network;

import java.io.Serializable;
import java.util.*;
import java.sql.Time;

public class Message implements Serializable{
    private User sender;
    private String message;
    private Date time;

    public Message(User sender, String message){
        this.sender = sender;
        this.message = message;
        this.time = java.util.Calendar.getInstance().getTime();
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate(){
        Time tm = new Time(this.time.getTime());
        return tm.toString();
    }
}
