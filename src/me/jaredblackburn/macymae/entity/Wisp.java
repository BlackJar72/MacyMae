package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.events.Message;
import static me.jaredblackburn.macymae.events.MsgType.WDIE;
import static me.jaredblackburn.macymae.events.MsgType.WNORMAL;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapException;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.Tile;

/**
 *
 * @author Jared Blackburn
 */
public class Wisp extends Entity {
    private float scaredTime;
    private final int GRAPHIC;
    private static final int SCARED_GRAPHIC 
            = Graphic.registry.getID("wispScared");
    private static final int DEAD_GRAPHIC
            = Graphic.registry.getID("wispDead");
    

    public Wisp(String image, int sx, int sy, float z, float secsPerFrame, 
            float baseSpeed, MoveCommand heading, IController brain) 
                throws MapException, Exception {
        super(image, sx, sy, z, secsPerFrame, baseSpeed, heading, 
                false, true, brain);
        scared = false;
        dead   = false;
        scaredTime = 0;
        GRAPHIC = Graphic.registry.getID(image);
    }
    
    
    @Override
    public void update(MapMatrix maze, float time, float delta) throws MapException {
        Tile currentTile = MapMatrix.getGameTile(x, y);
        if(dead) {
            if(MapMatrix.getGameTile(sx, sy) == currentTile) {
                dead = false;
                sendMsg(WNORMAL, this, brain);
                graphic = GRAPHIC;;
            }
        } else if(scared) {
            scaredTime -= Game.game.getPassedTime();
            if(scaredTime <= 0) {
                scared = false;
                sendMsg(WNORMAL, this, brain);
                graphic = GRAPHIC;
                scaredTime = 0f;
            }
        }
        super.update(maze, time, delta);
    }
    
    
    public void die() {
        graphic = DEAD_GRAPHIC;
        dead    = true;
        scared  = false;
        scaredTime = 0f;
    }
    

    @Override
    public void recieveMsg(Message msg) {
        brain.recieveMsg(msg);
        switch(msg.getContent()) {
            case POWERED:
                if(!dead) {
                    scared = true;
                    scaredTime = 10f;
                    graphic = SCARED_GRAPHIC;
                }
                break;
            case WDIE:
                die();
                break;
            default:                
                super.recieveMsg(msg);
        }
    }
    
    
    
    
}
