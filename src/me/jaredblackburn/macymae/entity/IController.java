package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.maze.Occupiable;

/**
 *
 * @author Jared Blackburn
 */
public interface IController extends IMsgReciever, IMsgSender {
        
    public MoveCommand getDirection(Occupiable loc);
    public void reset();
    
}
