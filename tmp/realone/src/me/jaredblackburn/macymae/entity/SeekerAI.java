package me.jaredblackburn.macymae.entity;

import java.util.Random;
import me.jaredblackburn.macymae.maze.Occupiable;
import me.jaredblackburn.macymae.maze.Tile;

/**
 *
 * @author Jared Blackburn
 */
public class SeekerAI extends EnemyAI {

    
    public SeekerAI(Random random, int sx, int sy) {
        super(random, sx, sy);
    }
    

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        if(!(loc instanceof Tile) || (loc == last)) {
            return current;
        }
        if(scared) {
            return flee(loc);
        }
        if(dead) {
            return goHome(loc);
        }
        return getPlayerDirection(loc);
    }
    
}
