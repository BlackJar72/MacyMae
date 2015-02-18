package me.jaredblackburn.macymae.panes;

import me.jaredblackburn.macymae.panes.GamePanel;
import javax.swing.JFrame;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapMatrix;

/**
 *
 * @author Jared Blackburn
 */
public class MainWindow extends JFrame {
    static MainWindow  window     = new MainWindow();
    static GamePanel   gamepanel  = new GamePanel();
    static SplashPanel notplaying = new SplashPanel();
    
    // The width and height of the window
    public static final int XLEN = Graphic.sideLength * (MapMatrix.WIDTH  + 2);    
    public static final int YLEN = Graphic.sideLength * (MapMatrix.HEIGHT + 2);
    
    
    
    
}
