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
        return last;
    }
    
    
    @Override
    // This should never actually be called
    public void recieveMsg(Message msg) {/*Do Nothing*/}
    
    
}
