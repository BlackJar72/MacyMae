package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.maze.MapMatrix;
import static me.jaredblackburn.macymae.maze.TileData.DOGPIN;

/**
 *
 * @author jared
 */
public class Wisp extends Entity {
    private boolean dead;

    public Wisp(int sx, int sy, float z, float speed, float BASE_SPEED, 
            float MSPF, IController brain, String graphic) {
        super(sx, sy, z, speed, BASE_SPEED, MSPF, false, true, brain, graphic);
        dead = false;
    }
    
    
    public void move(float delta, MapMatrix maze) {
        boolean inDogpin = locdat.contains(DOGPIN) || dead;
        switch(heading) {
            // TODO: Check allowed direction per tile...? Maybe...?
            case UP:
                if(maze.getTile(tx, (int)(ty + 0.5f)).canWispEnter(inDogpin))
                    y += speed * delta;
                break;
            case DOWN:
                if(maze.getTile(tx, (int)(ty - 0.5f)).canWispEnter(inDogpin)) 
                    y -= speed * delta;
                break;
            case LEFT:
                if(maze.getTile((int)(x - 0.5f), ty).canWispEnter(inDogpin)) 
                    x -= speed * delta;
                break;
            case RIGHT:
                if(maze.getTile((int)(x + 0.5f), ty).canWispEnter(inDogpin)) 
                    x += speed * delta;
                break;
            default:
                return;
        }
        adjustTile();
    }
    
}
