package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.TileData;
import static me.jaredblackburn.macymae.events.MsgType.*;

/**
 *
 * @author Jared Blackburn
 */
public class Entity implements IMsgSender, IMsgReciever {
    
    float  x,  y; // Actual x and y coords on screen 
    int   tx, ty; // Coordinates of the current tile
    int   sx, sy; // Coordinates of the entities start tile
    
    float speed;
    MoveCommand heading;
    private float BASE_SPEED;
    private EnumSet<TileData> locdat;
    
    int graphic;     // ID of the graphic used
    int frame;       // The frame of the graphic
    int numFrames;   // This may be replaced by a reference to the graphic itself
    long lastTime;   // Time of last update
    final long MSPF; // Miliseconds per frame
    
    boolean isPlayer;
    boolean isEnemy;
    
    public static Entity dog1;
    public static Entity dog2;
    public static Entity dog3;
    public static Entity dog4;
    public static Entity macy;
    
    // Arrays for iterations
    public static Entity[] dogs = new Entity[4];
    public static Entity[] entities = new Entity[5];
    
    IController brain; // Controls the behavior; could be AI or UI
    
    
    public static void init() {
        //TODO: Initialize all static entity references.
    }
    

    public Entity(float x, float y, int sx, int sy, float speed, 
            float BASE_SPEED, long lastTime, long MSPF, boolean isPlayer, 
            boolean isEnemy, IController brain) {
        // TODO: This autogenerated code, and may need some tweaking / additions
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.speed = speed;
        this.BASE_SPEED = BASE_SPEED;
        this.lastTime = lastTime;
        this.MSPF = MSPF;
        this.isPlayer = isPlayer;
        this.isEnemy = isEnemy;
        this.brain = brain;
    }
    
    
    void adjustTile() {
        // This is based on the assumption the casts truncate, which
        // seesm to work for Doomlike Dungeons; if wrong this will change.
        tx = (int)(x + 0.5f);
        ty = (int)(y + 0.5f);
    }
    
    
    public void setLocation(int x, int y) {
        this.x = (float)x + 0.5f;
        this.y = (float)y + 0.5f;
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
    
    
    public void move(float delta, MapMatrix maze) {
        locdat = maze.getTileData(tx, ty);
        switch(heading) {
            // TODO: Check allowed direction per tile...? Maybe...?
            case UP:
                if(locdat.contains(TileData.UP)) y += speed * delta;
                break;
            case DOWN:
                if(locdat.contains(TileData.DOWN)) y -= speed * delta;
                break;
            case LEFT:
                if(locdat.contains(TileData.LEFT)) y += speed * delta;
                break;
            case RIGHT:
                if(locdat.contains(TileData.RIGHT)) x -= speed * delta;
                break;
            default:
                return;
        }
        adjustTile();
        // Fix any rounding (etc.) based overshoots
        if(!locdat.contains(TileData.UP) && (y > ((float)ty + 0.5f))) {
            y = (float)ty + 0.5f;
        }
        if(!locdat.contains(TileData.DOWN) && (y < ((float)ty + 0.5f))) {
            y = (float)ty + 0.5f;
        }
        if(!locdat.contains(TileData.LEFT) && (x > ((float)tx + 0.5f))) {
            x = (float)tx + 0.5f;
        }
        if(!locdat.contains(TileData.RIGHT) && (x < ((float)tx + 0.5f))) {
            x = (float)tx + 0.5f;
        }        
    }
    
    
    public boolean testCollision(Entity other) {
        boolean out = Math.sqrt(((this.x - other.x) * (this.x - other.x)) 
                    + ((this.y - other.y) * (this.y - other.y))) > 0.9f;
        if(out) collideWith(other);
        return out;
    }
    
    
    public void collideWith(Entity other) {
        if(isPlayer && other.isEnemy) {
            // TODO: Send messages
        }
    }
    
    
    public void update(MapMatrix maze, long time, float delta) {
        if((time - lastTime) > MSPF) updateFrame();
        lastTime = time;
        move(delta, maze);
    }
    

    @Override
    public void recieveMsg(Message msg) {
        //TODO: Handle recieved message
        // It might be a good idea to pass this message on to the IController
        // (i.e., the AI) in most cases and handle some of it there.
        switch(msg.content) {
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
