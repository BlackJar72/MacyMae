package me.jaredblackburn.macymae.maze;

import java.util.ArrayList;
import java.util.EnumSet;
import me.jaredblackburn.macymae.entity.MoveCommand;

/**
 * This represents a graph edge connecting two tiles (acting as vertices).
 *
 * @author Jared Blackburn
 */
public class Connection extends Occupiable {
    
    
    public Connection(Tile tail, Tile head) {
        validMoves = EnumSet.noneOf(MoveCommand.class);
        occupantX = (head.x + tail.x) / 2;
        occupantY = (head.y + tail.y) / 2;
        if(head.x != tail.x) {
            validMoves.add(MoveCommand.LEFT);
            validMoves.add(MoveCommand.RIGHT);
            setsX = false;
            setsY = true;
            if(head.x < tail.x) {
                neighbors[1] = tail;
                neighbors[3] = head;
            } else {                
                neighbors[3] = tail;
                neighbors[1] = head;
            }
        } else {
            validMoves.add(MoveCommand.UP);
            validMoves.add(MoveCommand.DOWN);
            setsX = true;
            setsY = false;
            if(head.y < tail.y) {                
                neighbors[0] = tail;
                neighbors[2] = head;
            } else {                
                neighbors[2] = tail;
                neighbors[0] = head;
            }            
        }
    }
    
    
    private Connection(Connection ori) {
        id = ori.id;
        setsX = ori.setsX;
        setsY = ori.setsY;
        occupantX = ori.occupantX;
        occupantY = ori.occupantY;
        id = ori.id;
        validMoves = EnumSet.copyOf(ori.validMoves);
        neighbors = new Occupiable[4];
        System.arraycopy(ori.neighbors, 0, neighbors, 0, 4);        
    }
    
    
    @Override
    public Connection copy() {
        return new Connection(this);
    }
}
