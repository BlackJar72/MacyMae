package me.jaredblackburn.macymae.entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgType;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapException;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.Tile;

/**
 *
 * @author Jared Blackburn
 */
public class Entity implements IMsgSender, IMsgReciever {
    private int graphic;
    private int frame;
    private int numFrames;
    
    private float lastTime;
    private float secsPerFrame;
    
    private int locationID, lastID;
    private float x, y;
    private final float z;
    private int sx, sy;
    
    private float baseSpeed;
    private float speed;
    private MoveCommand heading;
    
    private boolean isPlayer, isEnemy;
    
    private IController brain;
    
    private static final Entity[] entities = new Entity[5];

    public Entity(int graphic, int frame, int numFrames, float lastTime, 
            float SecsPerFrame, int locationID, int lastID,  
            int sx, int sy, float z, float baseSpeed, float speed, 
            MoveCommand heading, boolean isPlayer, boolean isEnemy, 
            IController brain) {
        this.graphic = graphic;
        this.frame = frame;
        this.numFrames = numFrames;
        this.lastTime = lastTime;
        this.secsPerFrame = SecsPerFrame;
        this.locationID = locationID;
        this.lastID = lastID;
        this.x = this.sx = sx;
        this.y = this.sy = sy;
        this.z = z;
        this.baseSpeed = baseSpeed;
        this.speed = speed;
        this.heading = heading;
        this.isPlayer = isPlayer;
        this.isEnemy = isEnemy;
        this.brain = brain;
    }
    
    
    public static void init() {
        
    }
    
    
    protected void adjustTile() {}
    
    
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
//        for(Entity entity: entities)
//        {
//            entity.draw();
//        }
    }
    
    
    public void move(float delta, MapMatrix maze) {
        switch(heading) {
            // TODO: Check allowed direction per tile...? Maybe...?
            case UP:
                break;
            case DOWN:
                break;
            case LEFT:
                break;
            case RIGHT:
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
        }
    }
    
    
    public void update(MapMatrix maze, float time, float delta) {
        Tile currentTile = MapMatrix.getGameTile(x, y);
        try {
            locationID = MapMatrix.getOccupiableID(x, y, speed);
        } catch (MapException ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        }
        heading = brain.getDirection(MapMatrix.getOccupiableFromID(locationID));
        if((time - lastTime) >= secsPerFrame) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    ////////////////////////////////////////////////////////
    /*                  BASIC GETTERS                     */
    ////////////////////////////////////////////////////////
    public int getGraphic() {
        return graphic;
    }

    public int getFrame() {
        return frame;
    }

    public int getNumFrames() {
        return numFrames;
    }

    public float getLastTime() {
        return lastTime;
    }

    public float getSecsPerFrame() {
        return secsPerFrame;
    }

    public int getLocationID() {
        return locationID;
    }

    public int getLastID() {
        return lastID;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public float getSpeed() {
        return speed;
    }

    public MoveCommand getHeading() {
        return heading;
    }

    public boolean isIsPlayer() {
        return isPlayer;
    }

    public boolean isIsEnemy() {
        return isEnemy;
    }

    public IController getBrain() {
        return brain;
    }

    public static Entity[] getEntities() {
        return entities;
    }
    
    
    
    
}
