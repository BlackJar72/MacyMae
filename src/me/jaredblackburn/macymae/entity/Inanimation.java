package me.jaredblackburn.macymae.entity;

import static me.jaredblackburn.macymae.entity.MoveCommand.NONE;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgType;
import me.jaredblackburn.macymae.maze.Occupiable;

/**
 * This the controller for inanimate entities (e.g., bonus edibles); its purpose
 * is to have a proper IController for entities that do not move.  More to the
 * point, this is the no-AI AI, which will always give a move direction of none
 * and need not respond to messages (the entity itself handling things like its
 * own removal when it should no longer be present).
 * 
 * @author Jared Blackburn
 */
public class Inanimation implements IController {    

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        return NONE;
    }

    @Override
    public void reset() {}

    @Override
    public void recieveMsg(Message msg) {}

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {}
    
}
