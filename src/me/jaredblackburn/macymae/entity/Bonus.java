/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.entity;

import java.util.ArrayList;
import static me.jaredblackburn.macymae.entity.MoveCommand.NONE;
import me.jaredblackburn.macymae.game.Difficulty;
import me.jaredblackburn.macymae.maze.MapException;

/**
 *
 * @author jared
 */
public class Bonus extends Entity {
    private final int rank;  // Level of bonus
    private final int value; // Points its worth
    private float fistAppear, lastAppear, duration;
    
    private static final float BASE_FIRST    = 30f;
    private static final float BASE_LAST     = 90f;
    private static final float BASE_DURATION = 7f;
    private static final int   BASE_VALUE    = 64;
    private static final int   REPEATS       = 2;
    
    private static final ArrayList<Bonus> registry = new ArrayList<>();
    
    private Bonus(String image, int sx, int sy, int rank) throws Exception {
        super(image, sx, sy, 0.3f, Float.MAX_VALUE, 0f, NONE, false, false, new Inanimation());
        frame = this.rank = rank;
        value = BASE_VALUE << rank;
    }
    
    
    public static void init() {
        //TODO: This creates all the bonuses and places them in the registry.
    }
    
    
    public static Bonus getBonus(int gameLevel) {
        int level = gameLevel / REPEATS;
        if(level < 0) {
            level = 0;
        }
        if(level >= registry.size()) {
            level = registry.size();
        }
        Bonus out = registry.get(level);
        Difficulty dif = Difficulty.get(gameLevel);
        out.fistAppear = BASE_FIRST / dif.playerVFactor;
        out.lastAppear = BASE_LAST  / dif.playerVFactor;
        out.duration   = BASE_DURATION / dif.wispVFactor;
        return out;
    }
}
