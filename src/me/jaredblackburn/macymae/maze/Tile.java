package me.jaredblackburn.macymae.maze;

/**
 * 
 * @author Jared Blackburn
 */

import java.util.EnumSet;
import me.jaredblackburn.macymae.graphics.Graphic;
import static me.jaredblackburn.macymae.maze.MapMatrix.HEIGHT;
import static me.jaredblackburn.macymae.maze.TileData.*;
import me.jaredblackburn.macymae.ui.Window;
import static me.jaredblackburn.macymae.ui.Window.YSIZE;

/**
 *
 * @author jared
 */
public class Tile extends Occupiable {
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
            data.add(WALL);
        }
        this.x = x;
        this.y = y;
        xpos = ((x + 1) * Graphic.sideLength);
        ypos = ((HEIGHT - y + 1) * Graphic.sideLength);
        if((x == 0) || ((x % 2) == 0)) {
            if((y == 0) || ((y % 2) == 0)) zpos = -0.8f;
            else zpos = -0.81f;
        } else {
            if((y == 0) || ((y % 2) == 0)) zpos = -0.82f;
            else zpos = -0.83f;
        }
    }
    
    
    public void draw() {
        Graphic.draw(graphic, 0, xpos, ypos, zpos);
    }
    
    
    public void clear() {
        data.removeAll(cont);
        graphic = Graphic.registry.getID("empty");
    }
    
    
    /**
     * This detect if an entity is on this point.  Because movement is in
     * discrete hops (per frame) requiring coords to be equal would result
     * in primarily false negative where the point was hopped over; instead
     * this checks if they are closer than the distance than will be moved in
     * one frame (with some slight padding).
     * 
     * @param ox other's (entities) x
     * @param oy other's (entities) y
     * @param speed distance entity can move in one frame
     * @return true if close enough to call at this point, false otherwise
     */
    public boolean here(float ox, float oy, float speed) {
        return (((ox - x) * (ox -x)) + ((oy - y) * (oy - y)) 
                < (speed * speed * 1.1f));
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
    
    
    public boolean canPlayerEnter() {
        return !(data.contains(WALL) || data.contains(DOGPIN));
    }
    
    
    public boolean canWispEnter(boolean inDogpin) {
        return (!data.contains(WALL) && (data.contains(DOGPIN) == inDogpin));
    }
}
