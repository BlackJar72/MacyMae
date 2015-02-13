package me.jaredblackburn.macymae.events;

import java.util.ArrayList;

/**
 * This is a basic event queue for communicating important events between
 * parts of the game.  Technically this is implemented with a List rather
 * than a Queue because every event should be processed every frame, not
 * queued for later; also, since the list is cleared each frame and messages
 * are never removed at any other time elements should normally be in the order 
 * they were added anyway.
 * 
 * @author Jared Blackburn
 */
public class MsgQueue {
    private static final ArrayList<Message> queue = new ArrayList<>();
    
    
    public static void add(Message msg) {
        queue.add(msg);
    }
    
    
    public static void deliver() {
        for(Message msg : queue) {
            for(IMsgReciever recipient : msg.recipients) {
                recipient.recieveMsg(msg);
            }
        }
        queue.clear();
    }   
}
