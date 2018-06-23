package ua.pp.nedosika.chat.network;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class History implements Serializable{
    private List<Message> history;

    public History() {
        this.history = new ArrayList<Message>(10);
    }

    public void addMessage(Message message){
        if (this.history.size() > 10) this.history.remove(0);

        this.history.add(message);
    }

    public List<Message> getHistory(){
        return this.history;
    }
}
