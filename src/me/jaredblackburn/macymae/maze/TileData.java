package me.jaredblackburn.macymae.maze;

import java.util.EnumSet;
import me.jaredblackburn.macymae.entity.MoveCommand;

/**
 * This is an enumeration of features, transient or permanent, a tile may have.
 * Its primary purpose is to be used in EnumSet representing all the features
 * of a tile.
 * 
 * @author Jared Blackburn
 */
public enum TileData {   
    // Direction (passability) data starts at the top and move clockwise
    UP,
    RIGHT,
    DOWN,
    LEFT,
    // Non-direction data representing (non-entity) content of the tile
    FOOD,
    POWER,
    BONUS,
    DOGPIN;
    
    
    /**
     * This will take a byte of data, such as is read from map files, and 
     * convert it into a Java EnumSet for logical use in game.
     * 
     * @param data read for the tile
     * @return the same data as an EnumSet
     */
    public static EnumSet<TileData> makeSet(byte data) {
        // The data is probaby the exact byte created, but no way around this.
        EnumSet<TileData> out = EnumSet.noneOf(TileData.class);
        if((data & 1) != 0) {
            out.add(UP);
        } if((data & 2) != 0) {
            out.add(RIGHT);
        } if((data & 4) != 0) {
            out.add(DOWN);
        } if((data & 8) != 0) {
            out.add(LEFT);
        } if((data & 16) != 0) {
            out.add(FOOD);
        } if((data & 32) != 0) {
            out.add(POWER);
        } if((data & 64) != 0) {
            out.add(BONUS);
        } if((data & 128) != 0) {
            out.add(DOGPIN);
        } 
        return out;
    }
    
    
    /**
     * This will convert passable direction information contained in the tiles
     * data into valid moves for an entity.
     * 
     * @param in the tile occupied
     * @return MoveCommands that can work in the given tile
     */
    public static EnumSet<MoveCommand> validMoves(EnumSet<TileData> in) {
        EnumSet<MoveCommand> out = EnumSet.noneOf(MoveCommand.class);
        // Copying bits directly would be better, but not allowed by Java,
        // so using conditionals to add equivalent values.
        if(in.contains(UP))    out.add(MoveCommand.UP);
        if(in.contains(RIGHT)) out.add(MoveCommand.RIGHT);
        if(in.contains(DOWN))  out.add(MoveCommand.DOWN);
        if(in.contains(LEFT))  out.add(MoveCommand.LEFT);
        return out;
    }
    
    
    public static byte setToByte(EnumSet<TileData> in) {
        byte out = 0;
        if(in.contains(UP))     out |= 1;
        if(in.contains(RIGHT))  out |= 2;
        if(in.contains(DOWN))   out |= 4;
        if(in.contains(LEFT))   out |= 8;
        if(in.contains(FOOD))   out |= 16;
        if(in.contains(POWER))  out |= 32;
        if(in.contains(BONUS))  out |= 64;
        if(in.contains(DOGPIN)) out |= 128;
        return out;
    }
    
    
    public static int setToInt(EnumSet<TileData> in) {
        int out = 0;
        if(in.contains(UP))     out |= 1;
        if(in.contains(RIGHT))  out |= 2;
        if(in.contains(DOWN))   out |= 4;
        if(in.contains(LEFT))   out |= 8;
        if(in.contains(FOOD))   out |= 16;
        if(in.contains(POWER))  out |= 32;
        if(in.contains(BONUS))  out |= 64;
        if(in.contains(DOGPIN)) out |= 128;
        return out;
    }
}
