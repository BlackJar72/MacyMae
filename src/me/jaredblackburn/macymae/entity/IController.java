package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.maze.Occupiable;

/**
 *
 * @author jared
 */
public interface IController {
    
    
    public MoveCommand getDirection(Occupiable loc);
    
}
