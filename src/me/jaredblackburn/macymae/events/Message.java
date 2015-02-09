package me.jaredblackburn.macymae.events;

import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class Message {
    MsgType content;
    List<IMsgReciever> recipients;
    IMsgSender sender;
    
}
