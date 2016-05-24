package me.jaredblackburn.macymae.ui.graphics;

/**
 *
 * @author Jared Blackburn
 */
public class Font {
    // Maps pure, 7 bit, ascii characters to TBOs of there image
    private static final int[] mapping = new int[128];
    private static int blank;
    
    
    public static int getID(char in) {
        return mapping[(byte)in & 127];
    }
    
    
    public static void init() {
        String name;
        blank = Graphic.registry.getGraphic("blank").getID();
        for(int i = 0; i < 128; i++) {
            mapping[i] = blank;
        }
        for(Graphic gr : Graphic.registry) {
            name = gr.getName();
            if(name.length() == 1) {
                char it = name.charAt(0);
                mapping[(byte)it & 127] = gr.getID();
                if(Character.isDigit(it)) {
                    mapping[Character.digit(it, 10)] = gr.getID();
                }
            }
        }
    }
    
    
    public static void drawChar(char it, int xTile, int yTile) {
        Graphic.draw(mapping[(byte)it & 127], 0, xTile, yTile);
    }
    
    
    public static void drawString(String it, int xTile, int yTile) {
        int size = it.length();
        char[] string = it.toUpperCase().toCharArray();
        for(int i = 0; i < size; i++) {
            drawChar(string[i], xTile + i, yTile);
        }
    }
    
}
