package me.jaredblackburn.macymae.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.JPanel;
import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.ui.graphics.Font;
import me.jaredblackburn.macymae.ui.graphics.Graphic;

/**
 *
 * @author jared
 */
public class GamePanel extends JPanel implements IView {
    private final Toolkit tk = Toolkit.getDefaultToolkit();
    private final int wallpic = Graphic.registry.getID("wall");

    @Override
    public void draw() {
        drawBorder();
        MapMatrix.draw();
        Entity.drawAll();
        
        Font.drawString("Score: " + Game.player.getScore(), 1, 0);
        Font.drawString("Lives: " + Game.player.getLives(), 1, 1);
        Font.drawString("Level: " + Game.game.getLevel(),  29, 0);
        
        if(Game.game.getIsGameOver()) {
            Font.drawString("Game Over", 15, 15);
        }
        if(Game.game.getIsDemo()) {
            Font.drawString("Demo", 17, 11);            
        }
        Toast.draw();
        
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
    }
    
    
    @Override
    public void start() {}
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawImage(g);
        
    }
    
    
    private void drawImage(Graphics g) {
        Image img = Graphic.getGameScreen();
        if(img != null) {
            g.drawImage(img, WIDTH, WIDTH, 
                    this.getWidth(), 
                    this.getHeight(),
                    null);
        }
        Graphic.clearScreen();
    }
    
    
    private void drawBorder() {
        for(int i = 0; i < MapMatrix.WIDTH + 2; i++) {
            Graphic.draw(wallpic, 0, i, 2);
            Graphic.draw(wallpic, 0, i, MapMatrix.HEIGHT + 3);
        }
        for(int j = 2; j < MapMatrix.HEIGHT + 3; j++) {
            Graphic.draw(wallpic, 0, 0, j);
            Graphic.draw(wallpic, 0, MapMatrix.WIDTH + 1, j);
        }
    }
    
}
