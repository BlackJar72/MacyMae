package me.jaredblackburn.macymae.maze;

import java.util.EnumSet;
import me.jaredblackburn.macymae.entity.MoveCommand;

/**
 * This is is an abstract parent for explicit graph component, i.e., objects 
 * representing locations a game entity can occupy.
 * 
 * @author Jared Blackburn
 */
public abstract class Occupiable {
    /*
    //The following components represent where an entity can go from here
    // Each array codes directions in a clockwise direction starting from the
    // top:
    //
    // 0: UP
    // 1: RIGHT
    // 2: DOWN
    // 3: LEFT
    //
    // It is planned to use this conventions consistently so as to avoid 
    // confusion.
    */
    protected boolean setsX, setsY; // Do occupants get this coord from here?
    protected float   occupantX, occupantY;
    protected Occupiable[] neighbors = new Occupiable[4];
    protected EnumSet<MoveCommand> validMoves;
    protected int id;
    
    
    public EnumSet<MoveCommand> getValidMoves() {
        return validMoves;
    }
    
    
    public int getID() {
        return id;
    }
    
    
    public void setID(int id) {
        this.id = id;
    }
    
    
    public float giveX(float x) {
        if(setsX) return occupantX;
        else return x;
    }
    
    
    public float giveY(float y) {
        if(setsY) return occupantY;
        else return y;
    }
    
    
    public float getOccupantX() {
        return occupantX;
    }
    
    
    public float getOccupantY() {
        return occupantY;
    }
    
    
    
}
