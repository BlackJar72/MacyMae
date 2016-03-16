package me.jaredblackburn.macymae.ui.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Jared Blackburn
 */
public class GraphicRegistry extends ArrayList<Graphic> {
    private final HashMap<String, Integer> map = new HashMap<>();
    
    
    public void add(String name, Graphic graphic) {
        add(graphic);
        graphic.id   = indexOf(graphic);
        graphic.name = name;
        map.put(name, new Integer(graphic.id));
        System.out.println("Added Graphic " + name + " to registry with ID " 
                + getID(name));
    }
    
    
    public Graphic getGraphic(String name) {
        return get(map.get(name).intValue());
    }
    
    
    public int getID(String name) {
        return map.get(name).intValue();
    }
}
