package me.jaredblackburn.macymae.entity;

import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.game.Game;

/**
 *
 * @author Jared Blackburn
 */
public class InputControl implements IController {
    private MoveCommand last; // This will hold that last input until overwritten

    
    @Override
    public MoveCommand getDirection(Entity owner) {
        // TODO: Recieve a packet from User input and set heading
        last = owner.heading; // Temporary!!!
        return last;
    }
    
    
    public void recieveInput(MoveCommand in) {
        last = in;
    }
    
    
    @Override
    // This should never actually be called
    public void recieveMsg(Message msg) {/*Do Nothing*/}
    
    
}
