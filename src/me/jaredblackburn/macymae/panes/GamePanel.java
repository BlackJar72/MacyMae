package me.jaredblackburn.macymae.panes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import javax.swing.JPanel;
import me.jaredblackburn.macymae.graphics.Graphic;
import me.jaredblackburn.macymae.maze.MapMatrix;
import static me.jaredblackburn.macymae.panes.MainWindow.*;

/**
 * This whole system will probably go an be replaced with something using
 * LWJGL and OpenGL, since the results are entirely are not only incorrect
 * but entirely inconsistent (despite being given strictly static final data!
 * 
 * @author Jared Blackburn
 */
public class GamePanel extends JPanel {
    Graphics2D g2d;
    GridLayout layout;
    
    public GamePanel() {
        this.setName("gamepanel");
        this.setSize(XLEN, YLEN);
        this.layout = new GridLayout(MapMatrix.WIDTH + 2, MapMatrix.HEIGHT + 2);
        this.setLayout(layout);
    }
    
    
    public void drawBorder() {
        Graphic img = Graphic.registry.getGraphic("wall");
        System.out.println("creating border with Grahic " + img 
            + "; image " + img.getImage());
        g2d = (Graphics2D) this.getGraphics();
        for(int i = MapMatrix.WIDTH+2; i >= 0; i--) {
                g2d.drawImage(img.getImage(), i * Graphic.sideLength, 0, this);
                g2d.drawImage(img.getImage(), i * Graphic.sideLength, 
                        MapMatrix.HEIGHT * Graphic.sideLength, this);
            }        
        for(int j = MapMatrix.HEIGHT+1; j >= 1; j--) {
                g2d.drawImage(img.getImage(), 0, j * Graphic.sideLength, this);
                g2d.drawImage(img.getImage(), (MapMatrix.WIDTH+1) * Graphic.sideLength, 
                        j * Graphic.sideLength, this);
        }
    }
}
