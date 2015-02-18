package me.jaredblackburn.macymae.graphics;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

/**
 * This whole system will probably go an be replaced with something using
 * LWJGL and OpenGL, since the results are entirely are not only incorrect
 * but entirely inconsistent (despite being given strictly static final data!
 *
 * @author Jared Blackburn
 */
// TODO: Need to create and register one VBO for a 24x24 tile (two triagnles).
// TODO: A draw method allowing a Graphic to draw itself
public class Graphic {
    public static final GraphicRegistry registry = new GraphicRegistry();
    
    public static final int sideLength = 24; // Width/height in pixels
    
    private int[] frames;
    private int     pointer = 0;    
    
    
    public Graphic(int size) {
        frames = new int[size];
    }
    
    
    public Graphic(ArrayList<String> files) {
        frames = new int[files.size()];
        System.out.println("Found " + frames.length + " images to add");
        for(int i = 0; i < frames.length; i++) {
            frames[i] = loadImageGL(files.get(i));
        }
    }
    
    
    public int getImageID() {
        // For non-animated graphics, having only one image
        return frames[0];
    }
    
    
    public int getImageID(int idx) {
        // For non-animated graphics, having only one image
        return frames[idx];
    }
    
    
    public int getNextImage() {
        if(pointer >= frames.length) pointer = 0;
        return frames[pointer++]; // Post-increment (not pre-!) will it work?
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
    
    
    public static void addGraphic(String name, ArrayList<String> files) {
        System.out.println("Adding graphic " + name);
        Graphic graphic = new Graphic(files);
        registry.add(name, graphic);
    }
    
    public int loadImageGL(String address) {
        int pixel;
        int glID = glGenTextures();
        BufferedImage img = loadImage(address);
        ByteBuffer bytes = BufferUtils.createByteBuffer(img.getWidth()
                * img.getHeight() * 4);
        for(int i = 0; i < img.getWidth(); i++)
            for(int j = img.getHeight() - 1; j >= 0 ; j--) {
                pixel = img.getRGB(i, j);
                bytes.put((byte)((pixel >> 16) & 0xff));
                bytes.put((byte)((pixel >> 8) & 0xff));
                bytes.put((byte)(pixel & 0xff));
                bytes.put((byte)((pixel >> 24) & 0xff));
            }
        bytes.flip();
        glBindTexture(GL_TEXTURE_2D, glID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(),
                img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, bytes);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glBindTexture( GL_TEXTURE_2D, 0);
        return glID;
    }
    
}
