package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.maze.Occupiable;

/**
 *
 * @author Jared Blackburn
 */
public interface IController {
        
    public MoveCommand getDirection(Occupiable loc);
    public void recieveMsg(Message msg);
    
}
