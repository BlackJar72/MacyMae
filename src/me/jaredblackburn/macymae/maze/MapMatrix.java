package me.jaredblackburn.macymae.maze;

import java.util.ArrayList;
import java.util.EnumSet;
import me.jaredblackburn.macymae.entity.MoveCommand;

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
    private final ArrayList<Occupiable> locations;
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
            return current.getMapTile(sumX / n, sumY / n);
        }
        public boolean isEmpty() {
            return (n < 1);
        }
    }
    
    private final DotCenter initialDotCenter; // To calculate at at laod time
    
    
    public MapMatrix(int number, byte[][] data1, byte[][] data2) {
        initialDotCenter = new DotCenter();
        locations = new ArrayList<>();
        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT; j++) {
                data1[i][j] = (byte)(data1[i][j] | (byte)(data2[i][j] << 4));
                tiles[i][j] = new Tile(data1[i][j], i, j);
                locations.add(tiles[i][j]);
                tiles[i][j].setID(locations.indexOf(tiles[i][j]));
                if((tiles[i][j].data.contains(TileData.FOOD)) 
                   || (tiles[i][j].data.contains(TileData.POWER))) {
                    initialDotCenter.addTile(tiles[i][j]);                    
                }
            }
    }
    
    
    public static void init() throws MapException {
        for(MapMatrix maze : mazeRegistry) {
            maze.initConnections();
        }
        setCurrent(0);
    }
    
    
    public static void draw() {
        for(int i = 0; i < WIDTH; i++)
            for(int j = 0; j < HEIGHT; j++) {
                current.tiles[i][j].draw();
            }
    }
    
    
    public Tile getMapTile(int x, int y) {
        return tiles[x][y];
    }
    
    
    public static Tile getGameTile(float x, float y) {
        return current.tiles[(int)x][(int)y];
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
    
    
    private void initConnections() throws MapException {
        Connection edge;
        for(int j = 0; j < HEIGHT; j++)
            for(int i = 0; i < (WIDTH - 1); i++ )
            {
                if(tiles[i][j].validMoves.contains(MoveCommand.RIGHT)) {
                    if(!tiles[i+1][j].validMoves.contains(MoveCommand.LEFT)) {
                        throwInconsistency(i, j, i + 1, j);
                    }
                    edge = new Connection(tiles[i][j], tiles[i+1][j]);
                    locations.add(edge);
                    edge.setID(locations.indexOf(edge));
                    //System.out.println("Adding edge " + edge.id);
                    tiles[i][j].addConnection(edge, 1);
                    tiles[i+1][j].addConnection(edge, 3);
                } else if(tiles[i+1][j].validMoves.contains(MoveCommand.LEFT)) {
                    throwInconsistency(i, j, i + 1, j);
                }
            }
        for(int i = 0; i < WIDTH; i++) 
            for(int j = 0; j < (HEIGHT - 1); j++) {
                if(tiles[i][j].validMoves.contains(MoveCommand.DOWN)) {
                    if(!tiles[i][j+1].validMoves.contains(MoveCommand.UP)) {
                        throwInconsistency(i, j, i, j + 1);
                    }
                    edge = new Connection(tiles[i][j], tiles[i][j+1]);
                    locations.add(edge);
                    edge.setID(locations.indexOf(edge));
                    //System.out.println("Adding edge " + edge.id);
                    tiles[i][j].addConnection(edge, 0);
                    tiles[i][j+1].addConnection(edge, 2);
                } else if(tiles[i][j+1].validMoves.contains(MoveCommand.UP)) {
                    throwInconsistency(i, j, i, j + 1);
                }
                if(tiles[i][j+1].data.contains(TileData.DOGPIN) 
                        && !tiles[i][j].data.contains(TileData.DOGPIN)
                        && tiles[i][j].validMoves.contains(MoveCommand.UP)) {
                    edge = new Connection(tiles[i][j+1], tiles[i][j]);
                    locations.add(edge);
                    edge.setID(locations.indexOf(edge));
                    //System.out.println("Adding edge " + edge.id);
                    tiles[i][j].addConnection(edge, 0);
                    tiles[i][j+1].addConnection(edge, 2);
                }
            }
    }
    
    
    private void throwInconsistency(int x, int y, int x2, int y2) throws MapException {
        if(!(tiles[x][y].data.contains(TileData.DOGPIN) 
                || (tiles[x2][y2].data.contains(TileData.DOGPIN)))) {
            throw new MapException("Inconsistency in map at "
                            + x + ", " + y + " to " + x2 + ", " + y2);
        }
    }
    
    
    public static Occupiable getOccupiable(float x, float y, float speed) throws MapException {
        Tile loc = current.tiles[(int)x][(int)y];
        if(loc.here(x, y, speed)) {
            return loc;
        } else {
            if(x > (float)loc.x) return loc.neighbors[3];
            if(x < (float)loc.x) return loc.neighbors[1];
            if(y > (float)loc.y) return loc.neighbors[0];
            if(y < (float)loc.y) return loc.neighbors[2];
        }
        throw new MapException("Player at non-existant location at " 
                + x + "," + y + "!");
    }
    
    
    public static int getOccupiableID(float x, float y, float speed) 
            throws MapException {
        if(current.tiles[(int)x][(int)y].here(x, y, speed * 1.1f)) {
            return current.tiles[(int)x][(int)y].getID();
        } else {
            System.out.println(x + "," + y);
            if(x > (float)current.tiles[(int)x][(int)y].x) 
                return current.tiles[(int)x][(int)y].neighbors[1].getID();
            if(x < (float)current.tiles[(int)x][(int)y].x) 
                return current.tiles[(int)x][(int)y].neighbors[3].getID();
            if(y > (float)current.tiles[(int)x][(int)y].y) 
                return current.tiles[(int)x][(int)y].neighbors[0].getID();
            if(y < (float)current.tiles[(int)x][(int)y].y) 
                return current.tiles[(int)x][(int)y].neighbors[2].getID();
        }
        throw new MapException("Player at non-existant location at " 
                + x + "," + y + "!");
    }
    
    
    public static Occupiable getOccupiableFromID(int id) {
        return current.locations.get(id);
    }
}
