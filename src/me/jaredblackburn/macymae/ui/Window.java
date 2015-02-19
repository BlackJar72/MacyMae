/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author jared
 */
public class Window {
    public static final int XSIZE = (MapMatrix.WIDTH + 2) * Graphic.sideLength;
    public static final int YSIZE = (MapMatrix.HEIGHT + 2) * Graphic.sideLength;
    
    
    public Window() {
        try {            
            Display.create();   
            glClearColor(0f, 0f, 0f, 1f);            
            Display.setFullscreen(false);
            Display.setDisplayMode(new DisplayMode(XSIZE, YSIZE));
                        
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, XSIZE, 0, YSIZE, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            glClearColor(0.04f, 0.0f, 0.12f, 0.1f);
            glEnable(GL_DEPTH_TEST);
            
            glShadeModel(GL_SMOOTH);
            glEnable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);            
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            
            Display.setVSyncEnabled(true);
            
            glEnable(GL_CULL_FACE);
            glEnable(GL_CW);
            glCullFace(GL_BACK);
            glEnable(GL_DEPTH_TEST);
             glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);            
        } catch (LWJGLException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void draw() {
        drawBorder();
        MapMatrix.draw();
    }
    
    
    public final void cleanup() {
        Display.destroy();
    }
    
    
    private void drawBorder() {
        
    }
    
}
