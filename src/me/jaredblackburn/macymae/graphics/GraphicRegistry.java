package me.jaredblackburn.macymae.graphics;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jared Blackburn
 */
public class GraphicRegistry extends ArrayList<Graphic> implements List<Graphic> {
    private final HashMap<String, Integer> map = new HashMap<>();
    
    
    public void add(String name, Graphic graphic) {
        add(graphic);
        map.put(name, new Integer(indexOf(graphic)));
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
