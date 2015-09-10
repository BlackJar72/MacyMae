/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.ui;


import javax.swing.JPanel;
import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.ui.graphics.GLGraphic;
import me.jaredblackburn.macymae.ui.graphics.GLFont;
import me.jaredblackburn.macymae.maze.MapMatrix;

/**
 *
 * @author jared
 */

public class GameScreen implements IView {
    
    @Override
    public void draw() {
        drawBorder();
        MapMatrix.draw();
        Entity.drawAll();
        GLFont.drawString("Score: " + Game.player.getScore(), 1, 1);
        GLFont.drawString("Lives: " + Game.player.getLives(), 1, 2);
        GLFont.drawString("Level: " + Game.game.getLevel(),  29, 1);
        if(Game.game.getIsGameOver()) {
            GLFont.drawString("Game Over", 15, 15);
        }
        if(Game.game.getIsDemo()) {
            GLFont.drawString("Demo", 17, 11);            
        }
        Toast.draw();
    }
    
    
    @Override
    public void start(){}
    
    
    private void drawBorder() {
        for(int i = 0; i < MapMatrix.WIDTH + 2; i++) {
            GLGraphic.registry.getGraphic("wall").draw(0, 
                    (i + 0.5f) * GLGraphic.sideLength, 
                    0.5f * GLGraphic.sideLength, -0.99f);
            GLGraphic.registry.getGraphic("wall").draw(0, 
                    (i + 0.5f) * GLGraphic.sideLength, 
                    (MapMatrix.HEIGHT + 1.5f) * GLGraphic.sideLength, -0.99f);
        }
        for(int j = 1; j < MapMatrix.HEIGHT + 2; j++) {
            GLGraphic.registry.getGraphic("wall").draw(0, 
                    0.5f * GLGraphic.sideLength, 
                    (j + 0.5f) * GLGraphic.sideLength, -0.99f);
            GLGraphic.registry.getGraphic("wall").draw(0, 
                    (MapMatrix.WIDTH + 1.5f) * GLGraphic.sideLength, 
                    (j + 0.5f) * GLGraphic.sideLength, -0.99f);
        }
    }
    
}
