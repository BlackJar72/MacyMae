package me.jaredblackburn.macymae.events;

/**
 *
 * @author Jared Blackburn
 */
public interface IMsgSender {
    
    /**
     * 
     * @param message
     * @param recipients 
     */
    public void sendMsg(MsgType message, IMsgReciever... recipients);
    
}
