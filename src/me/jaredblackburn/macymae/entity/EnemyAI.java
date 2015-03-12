package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import java.util.Random;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.maze.Occupiable;
import me.jaredblackburn.macymae.maze.Tile;
import static me.jaredblackburn.macymae.maze.TileData.DOGPIN;

/**
 * A super-class for wisp AIs, also used as a stand-in and as a kind of random
 * AI.
 * 
 * @author Jared Blackburn
 */
public class EnemyAI implements IController {
    protected MoveCommand current = NONE, reverse = NONE;
    protected Random random = new Random(); // Later this will be passed in by contructor
    protected MoveCommand[] possibilities;
    protected Tile last;
    protected int die; // The "dice" value
    

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        if(!(loc instanceof Tile) || (loc == last)) {
            return current;
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
                possibilities = new MoveCommand[possible.size()];
                possibilities = possible.toArray(possibilities);
                die = random.nextInt(possibilities.length);
                current = possibilities[die];
            }
        }
        return current;
    }
    

    @Override
    public void recieveMsg(Message msg) {}
    
    
    protected MoveCommand seekTile(EnumSet<MoveCommand> available,
            Tile from, Tile target) {
        MoveCommand out = NONE;
        int shortest = Integer.MAX_VALUE;
        possibilities = new MoveCommand[available.size()];
        possibilities = available.toArray(possibilities);
        for(int i = 0; i < possibilities.length; i++) {
            int dist = findNewSquareDistance(possibilities[i], 
                    from.getX(), from.getY(), target.getX(), target.getY());
            if(dist < shortest) {
                out = possibilities[i];
                shortest = dist;
            }
        }
        return out;
    }
    
    
    protected MoveCommand seekCoords(EnumSet<MoveCommand> available,
            int x1, int y1, int x2, int y2) {
        MoveCommand out = NONE;
        int shortest = Integer.MAX_VALUE;
        possibilities = new MoveCommand[available.size()];
        possibilities = available.toArray(possibilities);
        for(int i = 0; i < possibilities.length; i++) {
            int dist = findNewSquareDistance(possibilities[i], 
                    x1, x2, y1, y2);
            if(dist < shortest) {
                out = possibilities[i];
                shortest = dist;
            }
        }
        return out;
    }
    
    
    private int findNewSquareDistance(MoveCommand dir, 
            int x1, int x2, int y1, int  y2) {
        int dx, dy;
        switch(dir) {
            case UP:
                dx = x1 - x2;
                dy = y1 - y2 + 1;
                break;
            case RIGHT:
                dx = x1 - x2 + 1;
                dy = y1 - y2;
                break;
            case DOWN:
                dx = x1 - x2;
                dy = y1 - y2 - 1;
                break;
            case LEFT:
                dx = x1 - x2 - 1;
                dy = y1 - y2;
                break;
            case NONE:
                dx = x1 - x2;
                dy = y1 - y2;
                break;
            default:
                throw new AssertionError(dir.name());
        
        }
        return (dx * dx) - (dy * dy);
    }
    
}
