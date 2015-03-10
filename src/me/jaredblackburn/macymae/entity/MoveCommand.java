package me.jaredblackburn.macymae.entity;

/**
 *
 * @author jared
 */
public enum MoveCommand {
    UP,
    RIGHT,
    DOWN,
    LEFT,
    NONE;
    
    public final byte bit;
    
    
    MoveCommand() {
        bit = (byte) (1 << ordinal());
    }
    
    
    public MoveCommand getReverse() {
        switch(this) { 
            case UP:
                return DOWN;
            case RIGHT:
                return LEFT;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case NONE:
                return NONE;
            default:
                throw new AssertionError(this.name());
        }
    }
    
    
}
