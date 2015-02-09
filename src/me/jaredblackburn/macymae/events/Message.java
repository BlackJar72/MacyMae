package me.jaredblackburn.macymae.events;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class Message {
    public final MsgType content;
    List<IMsgReciever> recipients;
    IMsgSender sender;
    
    
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
