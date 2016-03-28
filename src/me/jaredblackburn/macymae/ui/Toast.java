package me.jaredblackburn.macymae.ui;

import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.ui.graphics.Font;

/**
 *
 * @author jared
 */
public class Toast {
    private static float end       = -1f;       // Time to clear text
    private static boolean active  = false;  // Showing text
    private static String text     = "";     // Text to show
    
    
    private Toast(){}
    
    
    public static void set(String message) {
        text   = message;
        end    = Game.game.getTime() + 1.5f;
        active = true;
    }
    
    
    public static void unset() {
        active = false;
        end  = -1f;
        text = "";
    }
    
    
    public static void update() {
        if(active && (end <= Game.game.getTime())) {
            unset();
        }
    }
    
    
    public static void draw() {
        Font.drawString(text,  15, 1);
    }
    
    
    @Override
    public String toString() {
        return super.toString() + ": " + text;
    }    
}
