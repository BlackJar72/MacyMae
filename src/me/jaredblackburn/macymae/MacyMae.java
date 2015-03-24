package me.jaredblackburn.macymae;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.graphics.Font;
import me.jaredblackburn.macymae.resreader.GraphicsDataReader;
import me.jaredblackburn.macymae.resreader.MapReader;
import me.jaredblackburn.macymae.ui.UserInput;
import me.jaredblackburn.macymae.ui.Window;

/**
 * This is a simple Pac-Man clone named in honor of a neighbors cat, a fat cat
 * that always seemed to be hungry and would eat almost anything.  The goal is
 * to imitate the game play while using completely different graphics and maps
 * (thus to avoid copyright issues). 
 * 
 * This is also largely a practice / learning project (i.e., this is not 
 * intended as a commercial game and will likely be free or not officially 
 * released unlike other game writing projects I plan to come up with).
 * 
 * @author Jared Blackburn
 */
public class MacyMae {
    private static Window window;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        run();
        cleanup();
        
        /* TODO (currently):
           *1. Load and initialize map and graphics (DONE!)
            2. Implement game window / graphics (DONE!)
            3. Implement player controls (DONE!)
            4. Have game-less testable version (DONE!)
        */
        
        /* TODO (later):
            5. Implement enemy AI (DONE!)
            6. Add ememies to map (DONE!)
           *7. Add dots to map (DONE!)
            8. Add start, lives, points, and game-over (DONE!)
            9. Add non-game controls (DONE!)
        */ 
            
        /* TODO (last):
            10. Make and add final graphics (started)
            11. Make extra maps
            12. Declare done and release to friends; end project
        */
    }
    
    
    private static void init() {
        try {
            window = Window.getWindow();
            GraphicsDataReader.reader.openInfo();
            window.setIcon();
            MapReader.initMaps();
            Entity.init();
            UserInput.init();
            Graphic.init();
            Font.init();
        } catch (Exception ex) {
            Logger.getLogger(MacyMae.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
    
    
    private static void run() {
        Game.start(window);
    }
    
    
    private static void cleanup() {
        UserInput.cleanup();
        Graphic.cleanup();
        window.cleanup();
    }
    
    
    private static Window getWindow() {
        return window;
    }
    
}
