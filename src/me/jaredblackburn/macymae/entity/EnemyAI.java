package me.jaredblackburn.macymae.entity;

import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.Message;

/**
 *
 * @author Jared Blackburn
 */
public abstract class EnemyAI implements IController, IMsgReciever {
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
        //throw new UnsupportedOperationException("Not supported yet.");
        return NONE;
    }
    
    
    protected MoveCommand getDeadDirection(Entity owner) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return NONE;
    }
    
    
    @Override
    public void recieveMsg(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
