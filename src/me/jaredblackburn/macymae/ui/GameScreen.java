/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.ui;

import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapMatrix;
import static me.jaredblackburn.macymae.ui.Window.baseFPS;
import org.lwjgl.opengl.Display;

/**
 *
 * @author jared
 */
public class GameScreen implements IView {
    
    public void draw() {
        drawBorder();
        MapMatrix.draw();
        Entity.drawAll();
    }
    
    
    private void drawBorder() {
        for(int i = 0; i < MapMatrix.WIDTH + 2; i++) {
            Graphic.registry.getGraphic("wall").draw(0, 
                    (i + 0.5f) * Graphic.sideLength, 
                    0.5f * Graphic.sideLength, -0.99f);
            Graphic.registry.getGraphic("wall").draw(0, 
                    (i + 0.5f) * Graphic.sideLength, 
                    (MapMatrix.HEIGHT + 1.5f) * Graphic.sideLength, -0.99f);
        }
        for(int j = 1; j < MapMatrix.HEIGHT + 2; j++) {
            Graphic.registry.getGraphic("wall").draw(0, 
                    0.5f * Graphic.sideLength, 
                    (j + 0.5f) * Graphic.sideLength, -0.99f);
            Graphic.registry.getGraphic("wall").draw(0, 
                    (MapMatrix.WIDTH + 1.5f) * Graphic.sideLength, 
                    (j + 0.5f) * Graphic.sideLength, -0.99f);
        }
    }
    
}
