package me.jaredblackburn.macymae.entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import static me.jaredblackburn.macymae.events.MsgType.CAUGHT;
import static me.jaredblackburn.macymae.events.MsgType.WDIE;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapException;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.Occupiable;
import me.jaredblackburn.macymae.maze.Tile;

/**
 *
 * @author Jared Blackburn
 */
public class Entity implements IMsgSender, IMsgReciever {
    protected int graphic;
    protected int frame;
    
    private float lastTime;
    private final float secsPerFrame;
    
    protected boolean scared, dead;
    
    protected int locationID, lastID;
    protected float x, y;
    private final float z;
    protected final int sx, sy;
    
    private float baseSpeed;
    private float speed;
    private MoveCommand heading;
    
    private final boolean isPlayer, isEnemy;
    
    protected IController brain;
    
    public static Entity macy, wisp1, wisp2, wisp3, wisp4;
    private static final Entity[] entities = new Entity[5];
    
    
    public Entity(String image, int sx,  int sy, float z,
            float secsPerFrame, float baseSpeed, 
            MoveCommand heading, boolean isPlayer, boolean isEnemy, 
            IController brain) throws MapException, Exception {
        graphic = Graphic.registry.getID(image);
        this.frame = 0;
        this.lastTime = lastTime;
        this.secsPerFrame = secsPerFrame;
        this.locationID = locationID;
        this.lastID = lastID;
        this.x = this.sx = sx;
        this.y = this.sy = sy;
        this.z = z;
        speed = this.baseSpeed = baseSpeed;
        lastID = locationID = MapMatrix.getOccupiableID(x, y, speed);
        this.speed = speed;
        this.heading = heading;
        this.isPlayer = isPlayer;
        this.isEnemy = isEnemy;
        this.brain = brain;
    }
    
    
    public static void init() throws MapException, Exception {
        macy    = entities[0] = new Entity("macy", 18, 17, 0f, 0.04f, (1f / 10f), 
                            NONE, true, false, InputController.userio);
        wisp1  = entities[1] = new Wisp("wisp1", 16,  9, -0.11f, 0.03f, 1f / 20f,
                            NONE, new SeekerAI(Game.random, 16, 9));
        wisp2  = entities[2] = new Wisp("wisp2", 20,  9, -0.12f, 0.04f, 1f / 20f,
                            NONE, new EnemyAI(Game.random, 20, 9));
        wisp3  = entities[3] = new Wisp("wisp3", 16,  7, -0.13f, 0.05f, 1f / 20f, 
                            NONE, new LurkerAI(Game.random, 16, 7));
        wisp4  = entities[4] = new Wisp("wisp4", 20,  7, -0.14f, 0.04f, 1f / 20f, 
                            NONE, new GuardAI(Game.random, 20, 7));        
    }
    
    
    protected void adjustTile() {
        Occupiable loc = MapMatrix.getOccupiableFromID(locationID);
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
        if(frame >= Graphic.registry.get(graphic).size()) frame = 0;
    }
    
    
    public void draw() {
        Graphic.draw(graphic, frame, 
                (x + 1.5f) * Graphic.sideLength, 
                (MapMatrix.HEIGHT - y + 0.5f) * Graphic.sideLength, 
                z);
    }
    
    
    public static void drawAll() {
        for(Entity entity: entities)
        {
            entity.draw();
        }
    }
    
    
    public void move(float delta, MapMatrix maze) throws MapException {
        switch(heading) {
            // TODO: Check allowed direction per tile...? Maybe...?
            case UP:
                moveY(-speed * delta);
                x = MapMatrix.getOccupiableFromID(locationID).getOccupantX();
                break;
            case DOWN:
                moveY( speed * delta);
                x = MapMatrix.getOccupiableFromID(locationID).getOccupantX();
                break;
            case LEFT:
                moveX(-speed * delta);
                y = MapMatrix.getOccupiableFromID(locationID).getOccupantY();
                break;
            case RIGHT:
                moveX( speed * delta);
                y = MapMatrix.getOccupiableFromID(locationID).getOccupantY();
                break;
            default:
                return;
        }
        try {
            locationID = MapMatrix.getOccupiableID(x, y, speed * delta);
        } catch (Exception ex) {
            speed = 0f;
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    public void update(MapMatrix maze, float time, float delta) throws MapException {
        Tile currentTile = MapMatrix.getGameTile(x, y);
        try {
            locationID = MapMatrix.getOccupiableID(x, y, speed / delta);
        } catch (Exception ex) {
            speed = 0f;
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        }
        heading = brain.getDirection(MapMatrix.getOccupiableFromID(locationID));
        if((time - lastTime) >= secsPerFrame) {
            updateFrame();
            lastTime = time;
        }
       move(delta, maze);
       if(isPlayer && currentTile.here(x, y, 0.25f)) {
           currentTile.clear();
       }
    }
    
    
    public static void updateAll(MapMatrix maze, float time, float delta) {
        for(Entity entity : entities) {
            try {
                entity.update(maze, time, delta);
            } catch (MapException ex) {
                Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(int i = 1; i < entities.length; i++) {
            if(macy.testCollision(entities[i])) {
                if(entities[i].scared) {
                    macy.sendMsg(WDIE, Game.player, entities[i]);
                } else if(!entities[i].dead) {
                    macy.sendMsg(CAUGHT, Game.game, Game.player);
                    break;
                }
            }
        }
    }
    
    
    public void resetCoords() {
        x = (int)sx;
        y = (int)sy;
    }
    

    @Override
    public void recieveMsg(Message msg) {
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
                resetCoords();
                break;
            case POWERED:
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
    
    
    public Occupiable getOccupied() {
        return MapMatrix.getOccupiableFromID(locationID);
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
