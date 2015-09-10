/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.ui;

<<<<<<< HEAD
=======
import javax.swing.JPanel;
>>>>>>> e4fd358267bb54a5aa66b7b72c2a127e8cd8edff
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.ui.graphics.GLGraphic;

/**
 *
 * @author jared
 */
<<<<<<< HEAD
public class StartScreen implements IView {
=======
public class StartScreen extends JPanel implements IView {
>>>>>>> e4fd358267bb54a5aa66b7b72c2a127e8cd8edff
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
