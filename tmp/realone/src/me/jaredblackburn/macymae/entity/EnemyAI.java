package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import java.util.Random;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import static me.jaredblackburn.macymae.events.MsgType.POWERED;
import me.jaredblackburn.macymae.maze.MapMatrix;
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
    protected Random random;
    protected MoveCommand[] possibilities;
    protected Tile last;
    protected int die; // The "dice" value
    protected boolean scared, dead;
    protected int sx, sy;
    
    
    public EnemyAI(Random random, int sx, int sy) {
        this.random = random;
        this.sx = sx;
        this.sy = sy;
    }
    

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        if(!(loc instanceof Tile) || (loc == last)) {
            return current;
        }
        if(dead) {
            return goHome(loc);
        }
        if(scared) {
            return flee(loc);
        }
        return getRandomDirection(loc);
    }
    
    
    protected MoveCommand getRandomDirection(Occupiable loc) {        
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
    
    
    protected MoveCommand getPlayerDirection(Occupiable loc) {
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
                    (int)Entity.macy.getX(), (int)Entity.macy.getY());
            }
            
        }
        return current;        
    }
    

    @Override
    public void recieveMsg(Message msg) {
        MsgType message = msg.getContent();
        switch(message) {
            case START:
                break;
            case CLEARED:
                break;
            case NEXT:
                break;
            case STOP:
                break;
            case CAUGHT:
                break;
            case POWERED:
                if(!dead) {
                    MoveCommand tmp = current;
                    current = reverse;
                    reverse = tmp;
                    scared  = true;
                }
                break;
            case WNORMAL:
                    scared = false;
                    dead   = false;
                break;
            case WDIE:
                    scared = false;
                    dead   = true;
                break;
            case GAMEOVER:
                break;
            default:        
        }
    }
    
    
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
    
    
    protected MoveCommand avoidCoords(EnumSet<MoveCommand> available,
            int x1, int y1, int x2, int y2) {
        MoveCommand out = NONE;
        int longest = Integer.MIN_VALUE;
        possibilities = new MoveCommand[available.size()];
        possibilities = available.toArray(possibilities);
        for(int i = 0; i < possibilities.length; i++) {
            int dist = findNewSquareDistance(possibilities[i], 
                    x1, x2, y1, y2);
            if(dist > longest) {
                out = possibilities[i];
                longest = dist;
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
    

    public MoveCommand flee(Occupiable loc) {
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
                current = avoidCoords(possible, 
                    ((Tile)loc).getX(), ((Tile)loc).getY(),
                    (int)Entity.macy.getX(), (int)Entity.macy.getY());
            }            
        }
        return current;
    }
    

    public MoveCommand goHome(Occupiable loc) {
        last = (Tile)loc;
        int x = last.getX();
        int y = last.getY();
        EnumSet<MoveCommand> possible = loc.getValidMoves().clone();
        reverse = current.getReverse();
        if((x > 0) 
                && (MapMatrix.getGameTile(x-1, y).getData().contains(DOGPIN))) {
            possible.add(LEFT);
        }
        if((x < (MapMatrix.WIDTH - 1)) 
                && (MapMatrix.getGameTile(x+1, y).getData().contains(DOGPIN))) {
            possible.add(RIGHT);
        }
        if((y > 0) 
                && (MapMatrix.getGameTile(x, y-1).getData().contains(DOGPIN))) {
            possible.add(UP);
        }
        if((y < (MapMatrix.HEIGHT - 1)) 
                && (MapMatrix.getGameTile(x, y+1).getData().contains(DOGPIN))) {
            possible.add(DOWN);
        }
        possible.remove(reverse);        
        if(possible.isEmpty()) {
            current = reverse;
        } else {
            current = seekCoords(possible, x, y, sx, sy);
        }
        return current;
    }
    
    
    public void setDead(Boolean dead) {
        this.dead = dead;
    }
    
    
    public void setScared(Boolean scared) {
        this.scared = scared;
    }

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        MsgQueue.add(new Message(message, this, recipients));
    }

    @Override
    public void reset() {
        current = reverse = NONE;
    }
    
}
