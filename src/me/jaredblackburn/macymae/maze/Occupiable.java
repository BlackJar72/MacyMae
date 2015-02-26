package me.jaredblackburn.macymae.maze;

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
    protected boolean setsX, setY; // Do occupants get this coord from here?
    protected float   occupantX, occupantY;
    protected Occupiable[] neighbors;
    //protected EnumSet<MoveCommand> validMoves;
    
}
