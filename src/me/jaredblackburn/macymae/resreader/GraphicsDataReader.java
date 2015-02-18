package me.jaredblackburn.macymae.resreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.graphics.Graphic;

/**
 * The purpose of this class is too read a text file containTextinTextg data about the
 images used for graphics and their organization as frames inTextto Graphic 
 objects.
 * 
 * @author Jared Blackburn
 */
public final class GraphicsDataReader {
    public static final GraphicsDataReader reader = new GraphicsDataReader();
    
    private static final String loc     = "/res/pics/";
    private static final String infoLoc = loc + "GraphicsData.txt";
    
    // Line should be delimited with spaces, but including other stuff
    private static final String delimeters = " \t\n\r\f\"\';";
    // Block delimeters, showing all included images are frames of one graphic
    private static final String startBlock = "{";
    private static final String endBlock   = "}";
    // Lines starting with this will be treated as comments
    private static final String comment    = "#";
    private static final String empty      = "";
    
    private InputStream textStream;
    private BufferedReader inText;
    
    private String nextLine;
    private StringTokenizer tokens;
    private boolean inBlock = false;    
        
    private String name;
    private String address1;
    private String address2;
    private String token1;
    private String token2;
    private String token3;
    
    private ArrayList<String> list;
    
    
    private GraphicsDataReader() {};
    
    
    public void openInfo() {
        System.out.println("Running OpenInfo()");
        textStream = getClass().getResourceAsStream(infoLoc);
        if(textStream != null) {
            System.out.println("Trying to open file " + infoLoc);
            inText = new BufferedReader(new InputStreamReader(textStream));
        } else {
            System.err.println("ERROR! Could not get stream of " + infoLoc);
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
    
    
    private void parseInfo(BufferedReader in) throws IOException {
        System.out.println("Reading file " + infoLoc);
        while((nextLine = in.readLine()) != null) {
            System.out.println("Rading line: \"" + nextLine + "\"");
            if(nextLine.startsWith(comment)) continue;
            tokens = new StringTokenizer(nextLine, delimeters);
            token1 = getNextToken();
            token2 = getNextToken();
            token3 = getNextToken();
            if(inBlock) {
                if(token1.equals(endBlock)) {
                    inBlock = false;
                    makeGraphic();
                } else {
                    address2 = token1;
                    list.add(loc + token2 + token3);
                }
            } else {
                if(token1.equals(startBlock)) {
                    inBlock = true;
                } else {
                    list = new ArrayList<>();
                    name = token1;
                    if(token2.equals(startBlock)) {
                        inBlock = true;
                    } else {
                        address1 = token2;
                    }
                    if(token3.equals(startBlock)) {
                        inBlock = true;
                    } 
                    if(!inBlock) {
                        address2 = token3; 
                        list.add(loc + token2 + token3);
                        makeGraphic();
                    }
                }            
            }
        }
    }
    
    
    private String getNextToken() {
        if(tokens.hasMoreTokens()) {
            return tokens.nextToken();
        } else {
            return empty;
        }
    }
    
    
    private void makeGraphic() {
        Graphic.addGraphic(name, list);
    }
    
    
}
