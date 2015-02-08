package me.jaredblackburn.macymae.graphics;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jared Blackburn
 */
public class Graphic {
    private static final String baseLocation = "/me/jaredblackburn/res/";
        
    private Image[] frames;
    private int     pointer = 0;
    
    Graphic(int size) {
        // This will not be the usual way to initiate this class
        frames = new Image[size];
    }
    
    
    public Graphic(String name) {
        frames = new Image[1];
        frames[0] = loadImage(name);
    }
    
    
    public Graphic(String[] names) {
        frames = new Image[names.length];
        for(int i = 0; i < frames.length; i++) {
            frames[i] = loadImage(names[i]);
        }
    }
    
    
    /**
     * @param dir Name of the file or directory / package
     * @param isDir Whether or not to load all files in the directory as frames
     */
    public Graphic(String dir, boolean isDir) {
        //TODO: Create a way to turn a directory of images into frames here
    }
    
    
    public Image getImage() {
        // For non-animated graphics, having only one image
        return frames[0];
    }
    
    
    public Image getImage(int idx) {
        // For non-animated graphics, having only one image
        return frames[idx];
    }
    
    
    public Image getNextImage() {
        if(pointer >= frames.length) pointer = 0;
        return frames[pointer++]; // Post-increment (not pre-!) will it work?
    }
    
    
    private Image loadImage(String name) {
        try {
            return ImageIO.read(ImageIO
                    .createImageInputStream(new InputStreamReader(getClass()
                    .getResourceAsStream(baseLocation + name))));
            //return ImageIO.read(new File(name));
        } catch (IOException ex) {
            Logger.getLogger(Graphic.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
