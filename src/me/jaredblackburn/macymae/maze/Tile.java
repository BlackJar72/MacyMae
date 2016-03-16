package me.jaredblackburn.macymae.maze;

/**
 * 
 * @author Jared Blackburn
 */

import java.awt.Image;
import java.util.EnumSet;
import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import static me.jaredblackburn.macymae.events.MsgType.POWERED;
import me.jaredblackburn.macymae.game.Game;
import static me.jaredblackburn.macymae.maze.MapMatrix.HEIGHT;
import static me.jaredblackburn.macymae.maze.TileData.*;
import me.jaredblackburn.macymae.ui.graphics.Graphic;

/**
 *
 * @author jared
 */
public class Tile extends Occupiable implements IMsgSender {
    static final EnumSet<TileData> dirs = EnumSet.of(UP, LEFT, DOWN, RIGHT);
    static final EnumSet<TileData> cont = EnumSet.of(FOOD, POWER, BONUS);
    
    int graphic;
    Image swimg;
    EnumSet<TileData> data;  
    final int x, y;
    final int drawnx, drawny;   
    
    
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
        validMoves = TileData.validMoves(data);
        occupantX = this.x = x;
        occupantY = this.y = y;
        drawnx = x + 1;
        drawny = y + 3;
    }
    
    
    private Tile(Tile ori) {
        data = EnumSet.copyOf(ori.data);
        x = ori.x;
        y = ori.y;
        drawnx = ori.drawnx;
        drawny = ori.drawny;
        graphic = ori.graphic;
        id = ori.id;
        setsX = ori.setsX;
        setsY = ori.setsY;
        occupantX = ori.occupantX;
        occupantY = ori.occupantY;
        id = ori.id;
        validMoves = EnumSet.copyOf(ori.validMoves);
        neighbors = new Occupiable[4];
        System.arraycopy(ori.neighbors, 0, neighbors, 0, 4);
    }
    
    
    public void draw() {
        Graphic.draw(graphic, 0, drawnx, drawny);
    }
    

    @Override
    public Tile copy() {
        return new Tile(this);
    }
    
    
    public void clear() {
        if(data.contains(FOOD)) {  
            Game.player.incrementScore(15);
            Game.game.getDotCenter().subTile(this);
        }        
        if(data.contains(POWER)) {  
            Game.player.incrementScore(80);
            Game.game.getDotCenter().subTile(this);
            sendMsg(POWERED, Entity.wisp1, Entity.wisp2, 
                    Entity.wisp3, Entity.wisp4, Game.player);
        }
        data.removeAll(cont);
        graphic = Graphic.registry.getID("empty");
        if(Game.game.getDotCenter().isEmpty() 
                && !Game.game.getDotCenter().wasCleared()) {
            Game.game.getDotCenter().setCleared(true);
            sendMsg(MsgType.CLEARED, Game.game, Entity.macy,
                Entity.wisp1, Entity.wisp2, Entity.wisp3, Entity.wisp4);
        }
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
    
    
    public void addConnection(Connection con, int dir) {
        neighbors[dir] = con;
    }
    

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        MsgQueue.add(new Message(message, this, recipients));
    }
}
