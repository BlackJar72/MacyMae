package me.jaredblackburn.macymae.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.graphics.GLGraphic;
import me.jaredblackburn.macymae.maze.MapMatrix;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Jared Blackburn
 */
public class Window {
    public static final int baseFPS = 60;
    public static final int XSIZE = 800;
    public static final int YSIZE = (XSIZE * 2) / 3;
    public static final float scale = findScale();
    
    private IView currentScreen, gameScreen, startScreen;
    private static Window window;
    
    
    private Window() {
        try {
            Display.create();   
            glClearColor(0f, 0f, 0f, 1f);            
            Display.setFullscreen(false);
            Display.setResizable(false);
            Display.setTitle("Macy Mae");
            Display.setDisplayMode(new DisplayMode(XSIZE, YSIZE));
            System.out.println(Display.getDisplayMode());
                        
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, XSIZE, 0, YSIZE, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            glEnable(GL_DEPTH_TEST);
            
            glShadeModel(GL_SMOOTH);
            glEnable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);            
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            
            Display.setVSyncEnabled(true);
            
            glEnable(GL_DEPTH_TEST);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);  
            glMatrixMode(GL_MODELVIEW); 
        } catch (LWJGLException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        startScreen   = new StartScreen();
        gameScreen    = new GameScreen();
        currentScreen = startScreen;
    }
    
    
    public static Window getWindow() {
        if(window == null) {
            window = new Window();
        }
        return window;
    }
    
    
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        currentScreen.draw();
        Display.update();
        Display.sync(baseFPS);
    }
    
    
    public final void cleanup() {
        currentScreen = gameScreen = startScreen = null;
        Display.destroy();
    }
    
    
    private static float findScale() {
        float scale = (float)(XSIZE) 
                / (float)(GLGraphic.pixelWidth * (MapMatrix.WIDTH + 8.5));
        return (scale * scale);
    }
    
    
    public void switchScreens() {
        if(currentScreen == startScreen) {
            currentScreen = gameScreen;
        } else {
            currentScreen = startScreen;
        }
    }
    
    
    public void startGame() {
        currentScreen = gameScreen;        
    }
    
    
    public void endGame() {
        currentScreen = startScreen;
        startScreen.start();
    }
    
    
    public void setIcon() {
        Display.setIcon(GLGraphic.getIcons());
    }
    
}
