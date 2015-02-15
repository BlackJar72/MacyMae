package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.Message;

/**
 *
 * @author jared
 */
public class EnemyAI implements IController, IMsgReciever {
    protected MoveCommand forward, back; // Direction moving and the reverse

    
    @Override
    public MoveCommand getDirection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public void recieveMsg(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
