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
}
