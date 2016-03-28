package me.jaredblackburn.macymae.ui.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import me.jaredblackburn.macymae.maze.MapMatrix;

/**
 * This whole system will probably go an be replaced with something using
 * LWJGL and OpenGL, since the results are entirely are not only incorrect
 * but entirely inconsistent (despite being given strictly static final data!
 *
 * @author Jared Blackburn
 */

public class Graphic {
    public static final GraphicRegistry registry = new GraphicRegistry();
    
    public static final int   pixelWidth = 24;
    
    public static final int width  = pixelWidth * (MapMatrix.WIDTH  + 2);
    public static final int height = pixelWidth * (MapMatrix.HEIGHT + 4);
    
    private static Image startPic;    
    private static Image    gameI;
    private static Graphics gameG;
    
    public static ArrayList<ByteBuffer> icons = new ArrayList<>();
    
    int id;
    String name;    
    
    private BufferedImage[]   frames;
    private int               pointer = 0;    
    
    
    public Graphic(int size) {
        frames = new BufferedImage[size];
    }
    
    
    public static void init() {
        startPic = registry.getGraphic("start").getImage();
        
        gameI = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        gameG = gameI.getGraphics();
        
        gameG.setColor(Color.black);
        gameG.fillRect(0, 0, width, height);
    }
    
    
    public Graphic(ArrayList<String> files) {
        frames = new BufferedImage[files.size()];
        System.out.println("Found " + frames.length + " images to add");
        for(int i = 0; i < frames.length; i++) {
            frames[i] = loadImage(files.get(i));
        }
    }
    
    
    public BufferedImage getImage() {
        // For non-animated graphics, having only one image
        return frames[0];
    }
    
    
    public BufferedImage getImage(int idx) {
        // For non-animated graphics, having only one image
        return frames[idx];
    }
    
    
    public BufferedImage getNextImage() {
        pointer++;
        if(pointer >= frames.length) pointer = 0;
        return frames[pointer];
    }
    
    
    public static BufferedImage getNextImage(int ID) {
        Graphic graphic = registry.get(ID);
        graphic.pointer++;
        if(graphic.pointer >= graphic.frames.length) graphic.pointer = 0;
        return graphic.frames[graphic.pointer];
    }
    
    
    public void draw(int frame, float x, float y) {
        gameG.drawImage(frames[frame], 
                    (int)(x * pixelWidth), 
                    (int)(y * pixelWidth), 
                    null);
    }
    
    
    public static void drawReverse(int ID, int frame, float x, float y) {
        gameG.drawImage(registry.get(ID).frames[frame], 
                    (int)((x + 1) * pixelWidth), 
                    (int)(y * pixelWidth), 
                    -pixelWidth, 
                     pixelWidth,
                     null);
    }
    
    
    public static void draw(int ID, int frame, float x, float y) {
        gameG.drawImage(registry.get(ID).frames[frame], 
                    (int)(x * pixelWidth), 
                    (int)(y * pixelWidth), 
                    null);
    }
    
    
    public static void clearScreen() {
        gameG.clearRect(0, 0, width, height);
    }
    
    
    public static Image getTitle() {
        return startPic;
    }
    
    
    public static Image getGameScreen() {
        return gameI;
    }
    
    
    public static void addGraphic(String name, ArrayList<String> files) {
        System.out.println("Adding graphic " + name);
        Graphic graphic = new Graphic(files);
        registry.add(name, graphic);
    }
    
    
    private BufferedImage loadImage(String address) {
        System.out.println("Trying to load image " + address);
        try {
            BufferedInputStream stream = new BufferedInputStream(getClass()
                    .getResourceAsStream(address));
            ImageInputStream img;
            if(stream != null) {                
                img = ImageIO.createImageInputStream(stream);
            } else {
                System.err.println("ERROR! InpuStream was null!");
                img = null;
            }
            if(img != null) {
                return ImageIO.read(img);
            } else {
                System.err.println("ERROR! ImageInpuStream was null!");
                return null;
            }
            //return ImageIO.read(new File(address));
        } catch (IOException ex) {            
            Logger.getLogger(Graphic.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public int size() {
        return frames.length;
    }
    
    
    public static final void cleanup() { 
        for(Graphic graphic : registry) {
            for(int i = 0; i < graphic.frames.length; i++) {
                graphic.frames[i] = null;
            }
        }
        registry.clear();
    }
    
    
    public int getID() {
        return id;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public static ByteBuffer[] getIcons() {
        ByteBuffer[] out = new ByteBuffer[icons.size()];
        out = icons.toArray(out);
        return out;        
    }
    
}
