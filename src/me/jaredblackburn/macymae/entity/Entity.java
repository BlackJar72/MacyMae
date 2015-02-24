package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.TileData;
import static me.jaredblackburn.macymae.events.MsgType.*;
import static me.jaredblackburn.macymae.game.Game.game;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.Tile;

/**
 *
 * @author Jared Blackburn
 */
public class Entity implements IMsgSender, IMsgReciever {
    
    float  x,  y;  // Actual x and y used for rendering and collisions 
    final float z; // the z value used by OpenGL
    int   tx, ty;  // Coordinates of the current tile
    int   sx, sy;  // Coordinates of the entities start tile
    
    Tile  currentTile, lastTile;
    boolean newTile; // Has the entity changed tiles?
    
    float speed;
    MoveCommand heading;
    float BASE_SPEED;
    EnumSet<TileData> locdat;
    
    int graphic;     // ID of the graphic used
    int frame;       // The frame of the graphic
    int numFrames;   // This may be replaced by a reference to the graphic itself
    float lastTime;   // Time of last update
    final float MSPF; // Seconds per frame change
    
    boolean isPlayer;
    boolean isEnemy;
    
    // Enemies were dogs, but are now whisps
    public static Entity whisp1;
    public static Entity whisp2;
    public static Entity whisp3;
    public static Entity whisp4;
    // The player; will be drawn as Macy's head rather than a whole cat
    public static Entity macy;
    
    // Arrays for iterations
    public static Entity[] whisps   = new Entity[4];
    public static Entity[] entities = new Entity[5];
    
    final IController defaultBrain;
    IController brain; // Controls the behavior; could be AI or UI
    
    
    public static void init() {
        macy    = entities[4] = new Macy(18, 8, 0f, 0.05f, 0f, 1f / 30f, 
                            new InputControl(), "macy");
        whisp1  = entities[0] = new Wisp(16, 16, 0f, 0f, 0f, 1f / 30f,  
                            new InputControl(), "wisp1");
        whisp2  = entities[1] = new Wisp(20, 16, 0f, 0f, 0f, 1f / 30f,
                            new InputControl(), "wisp2");
        whisp3  = entities[2] = new Wisp(16, 18, 0f, 0f, 0f, 1f / 30f, 
                            new InputControl(), "wisp3");
        whisp4  = entities[3] = new Wisp(20, 18, 0f, 0f, 0f, 1f / 30f, 
                            new InputControl(), "wisp4");
        for(int i = 0; i < 4; i++) {
            whisps[i] = entities[i];
        }
    }
    

    public Entity(int sx, int sy, float z, float speed, 
            float BASE_SPEED, float MSPF, boolean isPlayer, 
            boolean isEnemy, IController brain, String graphic) {
        this.sx = sx;
        this.sy = sy;        
        setLocation(this.sx, this.sy);
        this.z = z;
        this.speed = speed;
        this.BASE_SPEED = BASE_SPEED;
        this.lastTime = lastTime;
        this.MSPF = MSPF;
        this.isPlayer = isPlayer;
        this.isEnemy = isEnemy;
        this.brain = this.defaultBrain = brain;
        this.graphic = Graphic.registry.getID(graphic);
        lastTime = 0f;
        numFrames = Graphic.registry.get(this.graphic).size();
        heading = LEFT;
    }
    
    
    protected void adjustTile() {
        tx = (int)(x);
        ty = (int)(MapMatrix.HEIGHT - y + 1f);
        currentTile = MapMatrix.getCurrent().getTile(tx, ty);
        newTile  = currentTile == lastTile;
        lastTile = currentTile;
        locdat = MapMatrix.getCurrent().getTileData(tx, ty);
    }
    
    
    public final void setLocation(int x, int y) {
        this.x = (float)x + 0.5f;
        this.y = (float)y + 1.0f;
        adjustTile();
    }
    
    
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
        adjustTile();
    }
    
    
    public void moveX(float vel) {
        x += vel;
        adjustTile();
    }
    
    
    public void moveY(float vel) {
        y += vel;
        adjustTile();
    }
    
    
    public void updateFrame() {
        frame++;
        if(frame >= numFrames) frame = 0;
    }
    
    
    public void draw() {
        Graphic.draw(graphic, frame, 
                (x + 0.5f) * Graphic.sideLength, 
                y * Graphic.sideLength, 
                z * Graphic.sideLength);
    }
    
    
    public static void drawAll() {
        for(Entity entity: entities)
        {
            entity.draw();
        }
    }
    
    
    public void move(float delta, MapMatrix maze) {
        switch(heading) {
            // TODO: Check allowed direction per tile...? Maybe...?
            case UP:
                if(!maze.getTile(tx, (int)(ty + 0.5f)).getData().contains(TileData.WALL))
                    y += speed * delta;
                break;
            case DOWN:
                if(!maze.getTile(tx, (int)(ty - 0.5f)).getData().contains(TileData.WALL)) 
                    y -= speed * delta;
                break;
            case LEFT:
                if(!maze.getTile((int)(x - 0.5f), ty).getData().contains(TileData.WALL)) 
                    x -= speed * delta;
                break;
            case RIGHT:
                if(!maze.getTile((int)(x + 0.5f), ty).getData().contains(TileData.WALL)) 
                    x += speed * delta;
                break;
            default:
                return;
        }
        adjustTile();
    }
    
    
    public boolean testCollision(Entity other) {
        boolean out = Math.sqrt(((this.x - other.x) * (this.x - other.x)) 
                    + ((this.y - other.y) * (this.y - other.y))) < 0.9f;
        if(out) collideWith(other);
        return out;
    }
    
    
    public void collideWith(Entity other) {
        if(isPlayer && other.isEnemy) {
            // FIXME: The game itself (or main loop) will also need to be notified
            sendMsg(CAUGHT, whisp1, whisp2, whisp3, whisp4, macy, game);
        }
    }
    
    
    public void update(MapMatrix maze, float time, float delta) {
        heading = brain.getDirection(this);
        if((time - lastTime) >= MSPF) {
            updateFrame();
            lastTime = time;
        }
       move(delta, maze);
       if(isPlayer) {
           //System.out.print("TileData in " + TileData.setToInt(locdat));
           currentTile.clear();
           //System.out.println("; TileData out " + TileData.setToInt(currentTile.getData()));
       }
    }
    
    
    public static void updateAll(MapMatrix maze, float time, float delta) {
        for(Entity entity : entities) {
            entity.update(maze, time, delta);
        }
    }
    

    @Override
    public void recieveMsg(Message msg) {
        if(isEnemy) brain.recieveMsg(msg);
        switch(msg.getContent()) {
            //TODO: Message handling hereSTART,
            case START:
                break;
            case CLEARED:
                break;
            case NEXT:
                break;
            case STOP:
                break;
            case CAUGHT:
                break;
            case POWERED:
                break;
            case PAUSE:
                break;
            case GAMEOVER:
                break;
            default:            
        }
    }
    

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        MsgQueue.add(new Message(message, this, recipients));
    }
    

    ////////////////////////////////////////////////////////
    /*                  BASIC GETTERS                     */
    ////////////////////////////////////////////////////////
    
    
    public float getX() {
        return x;
    }
    

    public float getY() {
        return y;
    }
    

    public int getTx() {
        return tx;
    }
    

    public int getTy() {
        return ty;
    }
    

    public int getSx() {
        return sx;
    }
    

    public int getSy() {
        return sy;
    }
    

    public int getGraphic() {
        return graphic;
    }
    

    public int getFrame() {
        return frame;
    }
    
    
    
    
}
