package me.jaredblackburn.macymae.maze;

import java.util.ArrayList;
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
    public static final int WIDTH  =  31; // PM had 27 (25 worth of dots)
    public static final int HEIGHT =  33; // PM had 31 (29 worth of dots)
    public static final int REPEATS = 3;  // Number of times a map repeats    
    private final Tile[][] tiles = new Tile[WIDTH][HEIGHT];
    private static final ArrayList<MapMatrix> mazeRegistry = new ArrayList<>();
    
    
    public MapMatrix(int number, byte[][] data1, byte[][] data2) {
        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT; j++) {
                data1[i][j] += (data2[i][j] << 4);
                tiles[i][j] = new Tile(data1[i][j]);
            }
    }
    
    
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
    
    
    public EnumSet<TileData> getTileData(int x, int y) {
        return tiles[x][y].data;
    }
    
    
    public int getTilePic(int x, int y) {
        return tiles[x][y].Graphic;
    }
    
    
    public static void add(int number, byte[][] data1, byte[][] data2) {
        mazeRegistry.add(number, new MapMatrix(number, data1, data2));
    }
}
