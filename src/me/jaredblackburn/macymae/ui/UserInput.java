package me.jaredblackburn.macymae.ui;

import me.jaredblackburn.macymae.entity.MoveCommand;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.MsgType;

/**
 *
 * @author Jared Blackburn
 */
public class UserInput implements IMsgSender {
    private static final float holdTime = 0.1f;
    
    
    private class Input {
       public MoveCommand last;
       public float timeHeld;
       public float timeReleased;
    }
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    /*                               MESSAGING                                */
    ////////////////////////////////////////////////////////////////////////////
    
    
    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
