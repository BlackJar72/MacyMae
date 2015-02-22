package me.jaredblackburn.macymae.graphics;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import me.jaredblackburn.macymae.ui.Window;
import static me.jaredblackburn.macymae.ui.Window.scale;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

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
    
    public static final int   pixelWidth = 24;
    public static final float sideLength = ((float)pixelWidth) * scale;
    public static final int vboid = makeTileVBO();
    
    private int[]   frames;
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
        pointer++;
        if(pointer >= frames.length) pointer = 0;
        return frames[pointer];
    }
    
    
    public static int getNextImage(int ID) {
        Graphic graphic = registry.get(ID);
        graphic.pointer++;
        if(graphic.pointer >= graphic.frames.length) graphic.pointer = 0;
        return graphic.frames[graphic.pointer];
    }
    
    
    public void draw(int frame, float x, float y, float z) {
        glPushMatrix();
            glTranslatef(x, y, z);
            glBindBuffer(GL_ARRAY_BUFFER, vboid);            
            glEnableClientState(GL_VERTEX_ARRAY);
            glVertexPointer(3, GL_FLOAT, 20, 0L);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            glTexCoordPointer(2, GL_FLOAT, 20, 12L);            
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, frames[frame]);
            glDrawArrays(GL_TRIANGLES, 0, 6);            
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
            glActiveTexture(GL_TEXTURE0);            
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        glPopMatrix();
    }
    
    
    public static void draw(int ID, int frame, float x, float y, float z) {
        glPushMatrix();
            glLoadIdentity();
            glTranslatef(x, y, z);
            glColor3f(1.0f, 1.0f, 1.0f);            
            glBindBuffer(GL_ARRAY_BUFFER, vboid);            
            glEnableClientState(GL_VERTEX_ARRAY);
            glVertexPointer(3, GL_FLOAT, 20, 0L);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            glTexCoordPointer(2, GL_FLOAT, 20, 12L);            
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, registry.get(ID).frames[frame]);
            glDrawArrays(GL_TRIANGLES, 0, 6);            
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);        
            glDisableClientState(GL_VERTEX_ARRAY);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        glPopMatrix();
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
    
    public int loadImageGL(String address) {
        int pixel;
        int glID = glGenTextures();
        BufferedImage img = loadImage(address);
        ByteBuffer bytes = BufferUtils.createByteBuffer(img.getWidth()
                * img.getHeight() * 4);
        for(int i = 0; i < img.getWidth(); i++)
            for(int j =  0; j < img.getHeight() ; j++) {
                pixel = img.getRGB(j, i);
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
    
    
    private static int makeTileVBO() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(2 * 3 * 5);
        float[] preBuffer = new float[]{0f,         0f,                   0f, 0f, 0f,
                               0f,                  (float)(-sideLength), 0f, 0f, 1f,
                               (float)(sideLength), (float)(-sideLength), 0f, 1f, 1f,
                               0f,                  0f,                   0f, 0f, 0f, 
                               (float)(sideLength), 0f,                   0f, 1f, 0f,
                               (float)(sideLength), (float)(-sideLength), 0f, 1f, 1f};
        }
        buffer.clear();
        buffer.put(preBuffer);
        buffer.flip();
        int vboid = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboid);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return vboid;
    }
    
    
    public static final void cleanup() {        
        // TODO: Release VBOID and TBOIDs after run
        glDeleteBuffers(vboid);
        for(Graphic graphic : registry) {
            for(int i = 0; i < graphic.frames.length; i++) {
                glDeleteBuffers(graphic.frames[i]);
            }
        }
    }
    
}
