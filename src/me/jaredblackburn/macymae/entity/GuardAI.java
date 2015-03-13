package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import java.util.Random;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.maze.Occupiable;
import me.jaredblackburn.macymae.maze.Tile;
import static me.jaredblackburn.macymae.maze.TileData.DOGPIN;

/**
 *
 * @author Jared Blackburn
 */
public class GuardAI extends EnemyAI {

    
    public GuardAI(Random random, int sx, int sy) {
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
        if((Game.game.getDotCenter().isEmpty()) || 
                (random.nextInt(Game.game.getDotCenter().getMax()) <
                Game.game.getDotCenter().getN())) {
            return getRandomDirection(loc);
        }
        last = (Tile)loc;
        EnumSet<MoveCommand> possible = loc.getValidMoves().clone();
        reverse = current.getReverse();
        possible.remove(reverse);        
        if(possible.isEmpty()) {
            current = reverse;
        } else {
            if(((Tile)loc).getData().contains(DOGPIN)) {
                current = seekCoords(possible, 
                    ((Tile)loc).getX(), ((Tile)loc).getY(),
                    17, 5);
            } else {
                current = seekCoords(possible, 
                    ((Tile)loc).getX(), ((Tile)loc).getY(),
                    Game.game.getDotCenter().getX(), 
                    Game.game.getDotCenter().getY());
            }
            
        }
        return current;
    }
    
}
