package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.events.Message;

/**
 *
 * @author Jared Blackburn
 */
public class InputControl implements IController {
    MoveCommand last; // This will hold that last input until overwritten

    
    @Override
    public MoveCommand getDirection(Entity owner) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    // This should never actually be called
    public void recieveMsg(Message msg) {/*Do Nothing*/}
    
    
}
