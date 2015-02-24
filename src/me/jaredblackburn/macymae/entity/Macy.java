package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.TileData;

/**
 *
 * @author jared
 */
public class Macy extends Entity {
    private boolean dead;

    public Macy(int sx, int sy, float z, float speed, float BASE_SPEED, 
            float MSPF, IController brain, String graphic) {
        super(sx, sy, z, speed, BASE_SPEED, MSPF, true, false, brain, graphic);
        dead = false;
    }
    
    
    public void move(float delta, MapMatrix maze) {
        if(dead) return;
        switch(heading) {
            // TODO: Check allowed direction per tile...? Maybe...?
            case UP:
                if(maze.getTile(tx, (int)(ty + 0.5f)).canPlayerEnter())
                    y += speed * delta;
                break;
            case DOWN:
                if(maze.getTile(tx, (int)(ty - 0.5f)).canPlayerEnter()) 
                    y -= speed * delta;
                break;
            case LEFT:
                if(maze.getTile((int)(x - 0.5f), ty).canPlayerEnter()) 
                    x -= speed * delta;
                break;
            case RIGHT:
                if(maze.getTile((int)(x + 0.5f), ty).canPlayerEnter()) 
                    x += speed * delta;
                break;
            default:
                return;
        }
        adjustTile();
    }
    
    
    
}
