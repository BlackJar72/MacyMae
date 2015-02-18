package me.jaredblackburn.macymae.panes;

import javax.swing.JFrame;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapMatrix;

/**
 * This whole system will probably go an be replaced with something using
 * LWJGL and OpenGL, since the results are entirely are not only incorrect
 * but entirely inconsistent (despite being given strictly static final data!
 * 
 * @author Jared Blackburn
 */
public class MainWindow extends JFrame {
    //static MainWindow  window     = new MainWindow();
    static GamePanel   gamepanel  = new GamePanel();
    static SplashPanel notplaying = new SplashPanel();
    
    static int border;
    
    // The width and height of the window
    public static final int XLEN = Graphic.sideLength * (MapMatrix.WIDTH  + 2);    
    public static final int YLEN = Graphic.sideLength * (MapMatrix.HEIGHT + 2);
    
    
    public MainWindow() {
        border = Graphic.registry.getID("wall");
        this.setSize(XLEN, YLEN);
        this.setVisible(true);
        this.setResizable(false);
        this.setEnabled(true);
        this.setName("MacyMae");
        this.setTitle("Macy Mae");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(gamepanel);
        gamepanel.setBounds(0, 0, XLEN, YLEN);
        
        
    }  
    
    
    public void postInit() {
        gamepanel.drawBorder();
    }
    
    
    
}
