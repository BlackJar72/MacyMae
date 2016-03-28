/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import java.util.Random;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.Occupiable;
import me.jaredblackburn.macymae.maze.Tile;
import static me.jaredblackburn.macymae.maze.TileData.DOGPIN;

/**
 *
 * @author jared
 */
public class InterceptorAI extends EnemyAI {

    public InterceptorAI(Random random, int sx, int sy) {
        super(random, sx, sy);
    }
    

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        if(!(loc instanceof Tile) || (loc == last)) {
            return current;
        }
        if(scared) {
            return flee(loc);
        }
        if(dead) {
            return goHome(loc);
        }
        return getPlayerDirection(loc);
    }
    
    
    @Override
    protected MoveCommand getPlayerDirection(Occupiable loc) {
        last = (Tile)loc;
        EnumSet<MoveCommand> possible = loc.getValidMoves().clone();
        reverse = current.getReverse();
        possible.remove(reverse);        
        if(possible.isEmpty()) {
            current = reverse;
        } else {
            if(((Tile)loc).getData().contains(DOGPIN)) {
                current = seekCoords(possible, 
                    ((Tile)loc).getX(), ((Tile)loc).getY(),
                    17, 5);
            } else {
                Tile destination = findPlayerTarget();
                current = seekCoords(possible, 
                    ((Tile)loc).getX(), ((Tile)loc).getY(),
                    destination.getX(), destination.getY());
            }            
        }
        return current;        
    }
    
    
    protected Tile findPlayerTarget() {        
        MoveCommand dir = Entity.macy.getHeading();
        int x = (int)Entity.macy.getX();
        int y = (int)Entity.macy.getY();
        int dx = 0, dy = 0;
        if(dir == MoveCommand.DOWN) {
            dy = +1;
        } else if (dir == MoveCommand.UP) {
            dy = -1;
        } else if (dir == MoveCommand.LEFT) {
            dx = -1;
        } else {
            dx = +1;
        }
        while(MapMatrix.getGameTile(x, y).getValidMoves().contains(dir)) {
            x += dx;
            y += dy;
        }
        return MapMatrix.getGameTile(x, y);
    }
}
