package me.jaredblackburn.macymae.entity;
import static java.lang.Math.random;
import java.util.EnumSet;
import java.util.Random;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.maze.Occupiable;
import me.jaredblackburn.macymae.maze.Tile;
import static me.jaredblackburn.macymae.maze.TileData.DOGPIN;
import me.jaredblackburn.macymae.maze.Occupiable;

/**
 *
 * @author jared
 */
public class LurkerAI extends SeekerAI {

    
    public LurkerAI(Random random, int sx, int sy) {
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
        if(random.nextBoolean()) {
            return getPlayerDirection(loc);
        } else {
            return getRandomDirection(loc);
        }
    }
    
}
