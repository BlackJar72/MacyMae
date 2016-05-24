package me.jaredblackburn.macymae.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.ui.graphics.Graphic;

/**
 *
 * @author jared
 */
public class StartPanel extends JPanel implements IView {
    private final Toolkit tk = Toolkit.getDefaultToolkit();
    private float timer;

    @Override
    public void draw() {
        Graphics g;
        try {
            g = this.getGraphics();
            if(g != null) {
                drawImage(g);
                tk.sync();
            }
            g.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(timer <= Game.game.getTime()) {
            Game.game.startDemo();
        }
    }
    
    
    @Override
    public void start() {
        timer = Game.game.getTime() + 15f;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawImage(g);
        
    }
    
    
    private void drawImage(Graphics g) {
        Image title = Graphic.getTitle();
        if(title != null) {
            g.drawImage(title, WIDTH, WIDTH, 
                    this.getWidth(), 
                    this.getHeight(),
                    null);
        }
        
    }
    
}
