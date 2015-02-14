package me.jaredblackburn.macymae.resreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The purpose of this class is too read a text file containTextinTextg data about the
 images used for graphics and their organization as frames inTextto Graphic 
 objects.
 * 
 * @author Jared Blackburn
 */
public class GraphicsDataReader {
    private static final String loc     = "res/pics/";
    private static final String infoLoc = loc + "GraphicsData.txt";
    
    private InputStream textStream;
    private BufferedReader inText;
    
    private String nextLine;
    private StringTokenizer tokens;
    private boolean inBlock = false;
    
    
    public void openInfo() {
        textStream = getClass().getResourceAsStream(infoLoc);
        if(textStream != null) {
            inText = new BufferedReader(new InputStreamReader(textStream));
        } else {
            // TODO: Handle error where file was not found or stream not created
        }
        if(inText != null) {
            try {
                parseInfo(inText);
                inText.close();
            } catch (IOException ex) {
                Logger.getLogger(GraphicsDataReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public void parseInfo(BufferedReader in) throws IOException {
        while((nextLine = in.readLine()) != null) {
            // Treat pound-sign / hash-tag as denoting comments
            if(nextLine.startsWith("#")) continue;
            // Line should be delimited with spaces, but including other stuff
            tokens = new StringTokenizer(nextLine, " \t\n\r\f\"\';");
        }
    }
    
    
}
