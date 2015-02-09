package me.jaredblackburn.macymae.events;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

/**
 *
 * @author Jared Blackburn
 */
public class MsgQueue {
    private static final ArrayDeque<Message> queue = new ArrayDeque<>();
    
    
    public static void add(Message msg) {
        queue.add(msg);
    }
    
}
