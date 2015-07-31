/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.ui;

import javax.swing.JPanel;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.ui.graphics.GLGraphic;

/**
 *
 * @author jared
 */
public class StartScreen extends JPanel implements IView {
    private float timer;

    @Override
    public void draw() {
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
