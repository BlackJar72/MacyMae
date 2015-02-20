package me.jaredblackburn.macymae.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapMatrix;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;

/**
 *
 * @author Jared Blackburn
 */
public class Window {
    public static final int XSIZE = (MapMatrix.WIDTH + 9) * Graphic.sideLength;
    public static final int YSIZE = (MapMatrix.HEIGHT + 5) * Graphic.sideLength;    
    public static final int XSCALE = (MapMatrix.WIDTH + 9) * Graphic.sideLength;
    public static final int YSCALE = (MapMatrix.HEIGHT + 5) * Graphic.sideLength;
    
    public Window() {
        try {            
            Display.create();   
            glClearColor(0f, 0f, 0f, 1f);            
            Display.setFullscreen(false);
            Display.setResizable(false);
            Display.setDisplayMode(new DisplayMode(XSIZE, YSIZE));
                        
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, XSCALE, 0, YSCALE, -1, 1);
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
    }
    
    
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        drawBorder();
        MapMatrix.draw();
        Display.update();
        Display.sync(60);
    }
    
    
    public final void cleanup() {
        Display.destroy();
    }
    
    
    private void drawBorder() {
        for(int i = 0; i < MapMatrix.WIDTH + 2; i++) {
            Graphic.registry.getGraphic("wall").draw(0, 
                    i * Graphic.sideLength, 
                    4 * Graphic.sideLength, -0.99f);
            Graphic.registry.getGraphic("wall").draw(0, 
                    i * Graphic.sideLength, 
                    (MapMatrix.HEIGHT + 5) * Graphic.sideLength, -0.99f);
        }
        for(int j = 5; j < MapMatrix.HEIGHT + 5; j++) {
            Graphic.registry.getGraphic("wall").draw(0, 2, j * Graphic.sideLength, -0.99f);
            Graphic.registry.getGraphic("wall").draw(0, 
                    (MapMatrix.WIDTH + 1) * Graphic.sideLength, 
                    j * Graphic.sideLength, -0.99f);
        }
    }
    
}
