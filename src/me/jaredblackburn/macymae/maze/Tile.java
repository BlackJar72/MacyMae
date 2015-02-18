package me.jaredblackburn.macymae.maze;

/**
 * 
 * @author Jared Blackburn
 */

import java.util.EnumSet;

/**
 *
 * @author jared
 */
public class Tile {
    int Graphic;
    EnumSet<TileData> data;  
    final int x, y;
    
    
    public Tile(byte bData, int x, int y) {
        data = TileData.makeSet(bData);
        // TODO: Graphics -- another data file?  Or derived from data?
        this.x = x;
        this.y = y;
    }
    
    
    @Override
    public boolean equals(Object other) {
        if(other instanceof Tile) {
            return (hashCode() == other.hashCode());
        } else return false;
    }
    
    
    @Override
    public int hashCode() {
        return (y * MapMatrix.WIDTH) + x;
    }
    

    public int getGraphic() {
        return Graphic;
    }
    

    public EnumSet<TileData> getData() {
        return data;
    }
    

    public int getX() {
        return x;
    }
    

    public int getY() {
        return y;
    }   
}
