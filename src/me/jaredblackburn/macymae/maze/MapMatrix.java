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
    // May tweak the values to make the game more different from the original game;
    // values turned out to be partially dictated by my graph paper...!
    public static final int WIDTH  =  37; // PM had 27 (25 worth of dots)
    public static final int HEIGHT =  25; // PM had 31 (29 worth of dots)
    public static final int REPEATS = 3;  // Number of times a map repeats    
    private final Tile[][] tiles = new Tile[WIDTH][HEIGHT];
    private final ArrayList<Connection> edges;
    private static final ArrayList<MapMatrix> mazeRegistry = new ArrayList<>();
    // I'm a little leary of going through a getter a lot in the inner loop
    // so this may change;
    private static MapMatrix current;
    
    public class DotCenter {
        int sumX = 0, sumY = 0, n = 0;
        public boolean subTile(Tile tile) {
            sumX -= tile.getX();
            sumY -= tile.getY();
            n--;
            return (n < 1);
        }        
        public void addTile(Tile tile) {
            sumX += tile.getX();
            sumY += tile.getY();
            n++;
        }
        public DotCenter copy() {
            DotCenter out = new DotCenter();
            out.sumX = this.sumX;
            out.sumY = this.sumY;
            out.n    = this.n;
            return out;
        }
        public int getX() {
            return sumX / n;
        }
        public int getY() {
            return sumY / n;
        }
        public Tile getTile() {
            return current.getTile(sumX / n, sumY / n);
        }
        public boolean isEmpty() {
            return (n < 1);
        }
    }
    
    private final DotCenter initialDotCenter; // To calculate at at laod time
    
    
    public MapMatrix(int number, byte[][] data1, byte[][] data2) {
        initialDotCenter = new DotCenter();
        edges = new ArrayList<>();
        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT; j++) {
                data1[i][j] = (byte)(data1[i][j] | (byte)(data2[i][j] << 4));
                tiles[i][j] = new Tile(data1[i][j], i, j);
                if((tiles[i][j].data.contains(TileData.FOOD)) 
                   || (tiles[i][j].data.contains(TileData.POWER))) {
                    initialDotCenter.addTile(tiles[i][j]);
                }
            }
    }
    
    
    public static void init() {
        setCurrent(0);
    }
    
    
    public static void draw() {
        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT; j++) {
                current.tiles[i][j].draw();
            }
    }
    
    
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }
    
    
    public EnumSet<TileData> getTileData(int x, int y) {
        return tiles[x][y].data;
    }
    
    
    public int getTilePic(int x, int y) {
        return tiles[x][y].graphic;
    }
    
    
    public static void add(int number, byte[][] data1, byte[][] data2) {
        mazeRegistry.add(number, new MapMatrix(number, data1, data2));
    }
    
    
    public static MapMatrix getMaze(int level) {
        return mazeRegistry.get((level / REPEATS) % mazeRegistry.size());
    }
    
    
    public static MapMatrix getCurrent() {
        return  current;
    }
    
    
    public static void setCurrent(int level) {
        current = getMaze(level);
    }
    
    
    // This is used to get a copy a DotCenter object that for actual use
    // in game by such systems as the GuardAI class.
    public DotCenter getUsuableDotCenter() {
        return initialDotCenter.copy();
    }
    
    
    private void initConnections() {
        // TODO: Create the connections
        // Plan, iterate each row and and connections between neighbors;
        // then, iterate each colomn and add connectiosn between neighbors.
    }
}
