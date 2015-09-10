package me.jaredblackburn.macymae.ui.graphics;

import static me.jaredblackburn.macymae.ui.graphics.GLGraphic.vboid;
import me.jaredblackburn.macymae.ui.Window;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

/**
 *
 * @author Jared Blackburn
 */
public class GLFont {
    // Maps pure, 7 bit, ascii characters to TBOs of there image
    private static int[] mapping = new int[128];
    private static int blank;
    
    
    public static int getTBO(char in) {
        return mapping[(byte)in & 127];
    }
    
    
    public static void init() {
        String name;
        blank = GLGraphic.registry.getGraphic("blank").getImageID();
        for(int i = 0; i < 128; i++) {
            mapping[i] = blank;
        }
        for(GLGraphic gr : GLGraphic.registry) {
            name = gr.getName();
            if(name.length() == 1) {
                char it = name.charAt(0);
                mapping[(byte)it & 127] = gr.getImageID();
                if(Character.isDigit(it)) {
                    mapping[Character.digit(it, 10)] = gr.getImageID();
                }
            }
        }
    }
    
    
    public static void drawChar(char it, int xTile, int yTile) {
        glPushMatrix();
            glTranslatef((xTile + 0.5f) * GLGraphic.sideLength, 
                    (Window.YSIZE / 1.375f) - ((yTile - 0.5f) * (GLGraphic.sideLength * 1.2f)), 
                    0.95f);
            glBindBuffer(GL_ARRAY_BUFFER, vboid);            
            glEnableClientState(GL_VERTEX_ARRAY);
            glVertexPointer(3, GL_FLOAT, 20, 0L);
            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            glTexCoordPointer(2, GL_FLOAT, 20, 12L);            
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, mapping[(byte)it & 127]);
            glDrawArrays(GL_TRIANGLES, 0, 6);            
            glDisableClientState(GL_TEXTURE_COORD_ARRAY);
            glActiveTexture(GL_TEXTURE0);            
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        glPopMatrix();
    }
    
    
    public static void drawString(String it, int xTile, int yTile) {
        int size = it.length();
        char[] string = it.toUpperCase().toCharArray();
        for(int i = 0; i < size; i++) {
            drawChar(string[i], xTile + i, yTile);
        }
    }
    
}
