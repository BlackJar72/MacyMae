package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.Message;

/**
 *
 * @author jared
 */
public class EnemyAI implements IController, IMsgReciever {
    protected MoveCommand back; // Direction moving and the reverse
    protected boolean alive;

    
    @Override
    public MoveCommand getDirection(Entity owner) {
         if(alive) {
             return getLiveDirection(owner);
         } else {
             return getDeadDirection(owner);
         }
    }
    
    
    protected MoveCommand getLiveDirection(Entity owner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    protected MoveCommand getDeadDirection(Entity owner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    @Override
    public void recieveMsg(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
