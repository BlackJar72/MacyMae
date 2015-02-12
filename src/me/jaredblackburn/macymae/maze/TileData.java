package me.jaredblackburn.macymae.maze;

import java.util.EnumSet;

/**
 *
 * @author jared
 */
public enum TileData {    
    UP,
    DOWN,
    LEFT,
    RIGHT,
    FOOD,
    POWER,
    BONUS,
    DOGPIN;
    
    
    public static EnumSet<TileData> makeSet(byte data) {
        EnumSet<TileData> out = EnumSet.noneOf(TileData.class);
        if((data & 1) != 0) {
            out.add(UP);
        } if((data & 2) != 0) {
            out.add(DOWN);
        } if((data & 1) != 0) {
            out.add(LEFT);
        } if((data & 1) != 0) {
            out.add(RIGHT);
        } if((data & 1) != 0) {
            out.add(FOOD);
        } if((data & 1) != 0) {
            out.add(POWER);
        } if((data & 1) != 0) {
            out.add(BONUS);
        } if((data & 1) != 0) {
            out.add(DOGPIN);
        } 
        return out;
    }
}
