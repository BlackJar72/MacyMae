package me.jaredblackburn.macymae;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.resreader.GraphicsDataReader;
import me.jaredblackburn.macymae.resreader.MapReader;
import me.jaredblackburn.macymae.ui.graphics.Font;
import me.jaredblackburn.macymae.ui.graphics.Graphic;
import me.jaredblackburn.macymae.ui.SwingWindow;

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
    private static SwingWindow window;
    private static Game game;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        run();
        cleanup();
        System.exit(0);
    }
    
    
    private static void init() {
        try {
            GraphicsDataReader.initGraphics();
            MapReader.initMaps();
            Entity.init();
            Graphic.init();
            Font.init();
            window = SwingWindow.getWindow();
            game = Game.getGame();
        } catch (Exception ex) {
            Logger.getLogger(MacyMae.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
    
    
    private static void run() {
        game.start(window);
    }
    
    
    private static void cleanup() {
        Graphic.cleanup();
        window.cleanup();
    }
    
    
    private static SwingWindow getWindow() {
        return window;
    }
    
}
