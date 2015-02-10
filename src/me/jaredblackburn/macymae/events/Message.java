package me.jaredblackburn.macymae.events;

// NOTE: I know that in real production code it would probably be best to 
// subclass the Java event class and us it.  However, this is largely a
// learning project and I wanted to explore the creation of messaging and
// event systems.  In some ways this is actually easier since I only
// implement what I need, the way I need it.

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class Message {
    final MsgType content;
    final IMsgSender sender;
    List<IMsgReciever> recipients;
    
    
    public Message(MsgType msg, IMsgSender sender, IMsgReciever... recipients) {
        this.content = msg;
        this.sender = sender;
        this.recipients = new ArrayList<IMsgReciever>();
        for(IMsgReciever reciever: recipients) {
            this.recipients.add(reciever);
        }
    }
    
    
    public Message(MsgType msg, IMsgSender sender, ArrayList recipients) {
        this.content = msg;
        this.sender = sender;
        this.recipients = recipients;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    /*                       GETTERS BELOW                                    */
    ////////////////////////////////////////////////////////////////////////////
    
    
    public void send() {
        MsgQueue.add(this);
    }
    

    public MsgType getContent() {
        return content;
    }
    

    public List<IMsgReciever> getRecipients() {
        return recipients;
    }
    

    public IMsgSender getSender() {
        return sender;
    }
    
    
}
