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
public class GraphicRegistry implements List<Graphic> {
    private final ArrayList<Graphic> list = new ArrayList<>();
    private final HashMap<String, Integer> map = new HashMap<>();
    
    
    public void add(String name, Graphic graphic) {
        list.add(graphic);
        map.put(name, new Integer(list.indexOf(graphic)));
        System.out.println("Added Graphic " + name + " to registry with ID " 
                + getID(name));
    }
    
    
    public Graphic getGraphic(String name) {
        return list.get(map.get(name).intValue());
    }
    
    
    public int getID(String name) {
        return map.get(name).intValue();
    }

    
    ////////////////////////////////////////////////////////////////////////////
    /*                     LIST INTERFACE WRAPPERS BELOW                      */
    ////////////////////////////////////////////////////////////////////////////
    
    
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<Graphic> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(Graphic e) {
        return list.add(e);
    }


    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Graphic> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Graphic> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Graphic get(int index) {
        return list.get(index);
    }

    @Override
    public Graphic set(int index, Graphic element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, Graphic element) {
        list.add(index, element);
    }

    @Override
    public Graphic remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Graphic> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Graphic> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<Graphic> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
