/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.ui;

import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.graphics.GLGraphic;
import me.jaredblackburn.macymae.graphics.GLFont;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 *
 * @author jared
 */
public class StartScreen implements IView {
    private float timer;

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GLGraphic.drawTitle();
        if(timer <= Game.game.getTime()) {
            Game.game.startDemo();
        }
    }
    
    
    @Override
    public void start() {
        timer = Game.game.getTime() + 15f;
    }
    
    
    
}
