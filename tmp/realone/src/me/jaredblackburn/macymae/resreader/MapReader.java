package me.jaredblackburn.macymae.resreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.maze.MapException;
import me.jaredblackburn.macymae.maze.MapMatrix;

/**
 *
 * @author Jared Blackburn
 */
public class MapReader {
    static final String loc  = "/res/maps";
    private BufferedReader in;
    
    
    private class mapData {
        public int mapNum;
        public byte[][] data1;
        public byte[][] data2;
    }
    
    
    private byte[][] readFile(String target) throws IOException {
        byte[][] out = new byte[MapMatrix.WIDTH][MapMatrix.HEIGHT];
        String line;
        int x = 0, y = 0;
        in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(target)));
        if(in != null) {
            while((line = in.readLine()) != null) {
                for(x = 0; x < line.length(); x++) {
                    out[x][y] = (byte)Character.digit(line.charAt(x), 16);                    
                }
                y++;
            }
            in.close();
        }
        return out;
    }
    
    
    private void readFiles() throws IOException {
        // Get a list of available maps
        ArrayList<String> mapFiles = new ArrayList<>();
        InputStream stream = getClass().getResourceAsStream(loc + "/maps.txt");
        in = new BufferedReader(new InputStreamReader(stream));
        mapData data = new mapData();
        String line;
        if(in != null) {
            while((line = in.readLine()) != null) {
                if((line.length() != 4) 
                  || (!line.startsWith("map")) 
                  || (!Character.isDigit(line.charAt(3)))) continue;
                mapFiles.add(line.substring(0, 4));
            }
        }
        for(String name : mapFiles) {
            data.mapNum = Character.digit(name.charAt(3), 10);
            data.data1 = readFile(loc + "/" + name + "A");
            data.data2 = readFile(loc + "/" + name + "B");
            MapMatrix.add(data.mapNum, data.data1, data.data2);
        }        
    }
    
    
    public static void initMaps() throws MapException {
        MapReader reader = new MapReader();
        try {
            reader.readFiles();
        } catch (IOException ex) {
            Logger.getLogger(MapReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        MapMatrix.init();
    }
    
}
