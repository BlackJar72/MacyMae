package me.jaredblackburn.macymae.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Jared Blackburn
 */
public class Graphic {
    public static final GraphicRegistry registry = new GraphicRegistry();
        
    private BufferedImage[] frames;
    private int     pointer = 0;    
    
    
    public Graphic(int size) {
        frames = new BufferedImage[size];
    }
    
    
    public Graphic(ArrayList<String> files) {
        frames = new BufferedImage[files.size()];
        for(int i = 0; i < frames.length; i++) {
            frames[i] = loadImage(files.get(i));
        }
    }
    
    
    public void addImage(BufferedImage image) {
        ArrayList<BufferedImage> out = new ArrayList<>();
        out.addAll(Arrays.asList(frames));
        out.add(image);
        frames = (BufferedImage[]) out.toArray();
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
        if(pointer >= frames.length) pointer = 0;
        return frames[pointer++]; // Post-increment (not pre-!) will it work?
    }
    
    
    private BufferedImage loadImage(String address) {
        try {
            return ImageIO.read(ImageIO
                    .createImageInputStream(new InputStreamReader(getClass()
                    .getResourceAsStream(address))));
            //return ImageIO.read(new File(address));
        } catch (IOException ex) {
            Logger.getLogger(Graphic.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public static void addGraphic(String name, ArrayList<String> files) {
        Graphic graphic = new Graphic(files);
        registry.add(name, graphic);
    }
    
}
