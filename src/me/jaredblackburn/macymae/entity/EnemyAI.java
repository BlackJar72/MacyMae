/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import java.util.Random;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.maze.Occupiable;
import me.jaredblackburn.macymae.maze.Tile;

/**
 *
 * @author jared
 */
public class EnemyAI implements IController {
    private MoveCommand current = NONE, reverse = NONE;
    private Random random = new Random(); // Later this will be passed in by contructor
    private MoveCommand[] possibilities;
    private Tile last;
    private int die; // The "dice" value

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        if(!(loc instanceof Tile) || (loc == last)) {
            return current;
        }
        last = (Tile)loc;
        EnumSet<MoveCommand> possible = loc.getValidMoves().clone();
        reverse = current.getReverse();
        System.out.print(current + " = -" + reverse + "; ");
        possible.remove(reverse);        
        if(possible.isEmpty()) {
            current = reverse;
        } else {
            possibilities = new MoveCommand[possible.size()];
            possibilities = possible.toArray(possibilities);
            for(int i = 0; i < possibilities.length; i++) {
                System.out.print(possibilities[i] + " ");
            }
            die = random.nextInt(possibilities.length);
            current = possibilities[die];
        }
        System.out.println(" -> " + current);
        return current;
    }
    

    @Override
    public void recieveMsg(Message msg) {}
    
}
