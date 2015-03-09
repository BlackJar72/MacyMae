package me.jaredblackburn.macymae.entity;

/**
 *
 * @author Jared Blackburn
 */
public class Entity {
    private int graphic;
    private int frame;
    private int numFrames;
    
    private float lastTime;
    private float SecsPerFrame;
    
    private int locationID, lastID;
    private float x, y;
    private int sx, sy;
    
    private float baseSpeed;
    private float speed;
    private MoveCommand heading;
    
    private boolean isPlayer, isEnemy;
    
    private IController brain;
    
    
    
    
}
