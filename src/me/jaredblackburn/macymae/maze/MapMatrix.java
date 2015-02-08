package me.jaredblackburn.macymae.maze;

import java.util.EnumSet;

/**
 * A class to hod the board / maze data.
 * 
 * (This should probably be in a sub-package, but here for now -- then will I 
 *  need any sub-packages.)
 * 
 * @author Jared Blackburn
 */
public class MapMatrix {
    // May tweak the values to make the game more different from the original game
    public static final int WIDTH  = 31; // PM had 27 (25 worth of dots)
    public static final int HEIGHT = 33; // PM had 31 (29 worth of dots)
    Tile[][] tiles = new Tile[WIDTH][HEIGHT];
    
    
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
    
    
    public EnumSet<TileData> getTileData(int x, int y) {
        return tiles[x][y].data;
    }
    
    
    public int getTilePic(int x, int y) {
        return tiles[x][y].Graphic;
    }
}
