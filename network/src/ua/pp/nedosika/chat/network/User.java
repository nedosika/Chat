package ua.pp.nedosika.chat.network;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
    private int level = 0;

    public User(){

    }

    public User(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
