package me.jaredblackburn.macymae.maze;

/**
 * 
 * @author Jared Blackburn
 */

import java.util.EnumSet;
import me.jaredblackburn.macymae.graphics.Graphic;
import static me.jaredblackburn.macymae.maze.TileData.*;

/**
 *
 * @author jared
 */
public class Tile {
    static final EnumSet<TileData> dirs = EnumSet.of(UP, LEFT, DOWN, RIGHT);
    static final EnumSet<TileData> cont = EnumSet.of(FOOD, POWER, BONUS);
    
    int graphic;
    EnumSet<TileData> data;  
    final int x, y;
    final float xpos, ypos, zpos;    
    
    
    public Tile(byte bData, int x, int y) {
        data = TileData.makeSet(bData);
        if(data.contains(POWER)) {
            graphic = Graphic.registry.getID("power");
        } else if(data.contains(DOGPIN)) {
            graphic = Graphic.registry.getID("dogpin");            
        } else if(data.contains(FOOD)) {
            graphic = Graphic.registry.getID("food");
        } else if( data.contains(UP) 
                || data.contains(LEFT) 
                || data.contains(DOWN) 
                || data.contains(RIGHT)) {
            graphic = Graphic.registry.getID("empty");
        } else {
            graphic = Graphic.registry.getID("wall");
        }
        this.x = x;
        this.y = y;
        xpos = x * Graphic.sideLength;
        ypos = y * Graphic.sideLength;
        if((x == 0) || ((x % 2) == 0)) {
            if((y == 0) || ((y % 2) == 0)) zpos = -0.8f;
            else zpos = -0.81f;
        } else {
            if((y == 0) || ((y % 2) == 0)) zpos = -0.82f;
            else zpos = -0.83f;
        }
    }
    
    
    public void clear() {
        data.removeAll(cont);
        graphic = Graphic.registry.getID("empty");
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
        return graphic;
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
