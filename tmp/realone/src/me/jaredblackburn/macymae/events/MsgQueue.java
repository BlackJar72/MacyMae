package me.jaredblackburn.macymae.events;

import java.util.ArrayList;

/**
 * This is a basic event queue1 for communicating important events between
 parts of the game.  Technically this is implemented with a List rather
 than a Queue because every event should be processed every frame, not
 queue1d for later; also, since the list is cleared each frame and messages
 are never removed at any other time elements should normally be in the order 
 they were added anyway.
 * 
 * @author Jared Blackburn
 */
public class MsgQueue {
    // Double buffering the queue to allow message to be sent in
    // responce to other messages; notably this is used to trigger
    // GAMEOVER in responce to CAUGHT when the player is out of lives.
    private static ArrayList<Message> queue1 = new ArrayList<>();
    private static ArrayList<Message> queue2 = new ArrayList<>();
    
    
    public static void add(Message msg) {
        queue1.add(msg);
    }
    
    
    public static void deliver() {        
        ArrayList<Message> tmp = queue2;
        queue2 = queue1;
        queue1 = tmp;
        for(Message msg : queue2) {
            for(IMsgReciever recipient : msg.recipients) {
                recipient.recieveMsg(msg);
            }
        }
        queue2.clear();
    }   
}
