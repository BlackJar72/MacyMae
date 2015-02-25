package me.jaredblackburn.macymae;

import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.resreader.GraphicsDataReader;
import me.jaredblackburn.macymae.resreader.MapReader;
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
            2. Implement game window / graphics (partly done)
            3. Implement player controls
            4. Have game-less testable version (move through maze, no goals)
        */
        
        /* TODO (later):
            5. Implement enemy AI
            6. Add ememies to map
           *7. Add dots to map (DONE!)
            8. Add start, lives, points, and game-over
            9. Add non-game controls / menues (start / quit); swing?
        */ 
            
        /* TODO (last):
            10. Make and add final graphics (started)
            11. Make extra maps
            12. Declare done and release to friends; end project
        */
    }
    
    
    private static void init() {
        window = new Window();
        GraphicsDataReader.reader.openInfo();
        MapReader.initMaps();
        MapMatrix.init();
        //Entity.init();
        //UserInput.init();
    }
    
    
    private static void run() {
        Game.start(window);
    }
    
    
    private static void cleanup() {
        Graphic.cleanup();
        window.cleanup();
    }
    
}
