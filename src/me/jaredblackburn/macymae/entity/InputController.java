package me.jaredblackburn.macymae.entity;

import java.util.EnumSet;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.maze.Occupiable;

/**
 *
 * @author Jared Blackburn
 */
public class InputController implements IController {
    public static final InputController userio = new InputController();
    private MoveCommand current = NONE;
    private class Commands {
        public int[] time = new int[]{0, 0, 0, 0, 0};
        public boolean[] updated 
                = new boolean[]{false, false, false, false, false};
    }
    private final Commands commands = new Commands();
    

    
    @Override
    public MoveCommand getDirection(Occupiable loc) {
        MoveCommand in = findLatestCommand();
        // Only consider input if it is for a valid directions
        if(loc.getValidMoves().contains(in)) {
            current = in;
        }
        // Only allow movement in a direction that is valid, reguardless of 
        // input
        if(!loc.getValidMoves().contains(current)) {
            current = NONE;
        }
        return current;
    }
    
    
    public void update(EnumSet<MoveCommand> in) {
        for(MoveCommand com : in) {
            commands.updated[com.ordinal()] = true;
        }
        for(int i = 0; i < 5; i++) {
            if(commands.updated[i]) {
                commands.time[i]++;
            } else {
                commands.time[i] = 0;
            }
            commands.updated[i] = false;
        }        
    }
    
    
    private MoveCommand findLatestCommand() {
        int out = 5;
        long shortest = Integer.MAX_VALUE;
        for(int i = 0; i < 5; i++) {
            if((commands.time[i] > 0) && (commands.time[i] < shortest)) {
                shortest = commands.time[i];
                out = i;
            }            
        }
        switch(out) {
            case 0: return UP;
            case 1: return RIGHT;
            case 2: return DOWN;
            case 3: return LEFT;
            default: return NONE;
        }
    }  
    

    @Override
    public void recieveMsg(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  
}
