/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import me.jaredblackburn.macymae.maze.Occupiable;
import me.jaredblackburn.macymae.maze.Tile;
import static me.jaredblackburn.macymae.maze.TileData.DOGPIN;

/**
 *
 * @author jared
 */
public class GuardAI extends EnemyAI {
    

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        if(!(loc instanceof Tile) || (loc == last)) {
            return current;
        }
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
                // TODO: Seek dot center
            }
            
        }
        return current;
    }
    
}
