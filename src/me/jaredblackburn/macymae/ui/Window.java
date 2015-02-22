package me.jaredblackburn.macymae.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.graphics.Graphic;
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
    public static final int XSIZE = 800;
    public static final float scale = findScale();
    public static final int YSIZE = (XSIZE * 2) / 3;
    
    public Window() {
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
    }
    
    
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        drawBorder();
        MapMatrix.draw();
        Entity.drawAll();
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
                    1 * Graphic.sideLength, -0.99f);
            Graphic.registry.getGraphic("wall").draw(0, 
                    i * Graphic.sideLength, 
                    (MapMatrix.HEIGHT + 2) * Graphic.sideLength, -0.99f);
        }
        for(int j = 1; j < MapMatrix.HEIGHT + 2; j++) {
            Graphic.registry.getGraphic("wall").draw(0, 0, j * Graphic.sideLength, -0.99f);
            Graphic.registry.getGraphic("wall").draw(0, 
                    (MapMatrix.WIDTH + 1) * Graphic.sideLength, 
                    j * Graphic.sideLength, -0.99f);
        }
    }
    
    
    private static float findScale() {
        float scale = (float)(XSIZE) 
                / (float)(Graphic.pixelWidth * (MapMatrix.WIDTH + 8.5));
        return (scale * scale);
    }
    
}
