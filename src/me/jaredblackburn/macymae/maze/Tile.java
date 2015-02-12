/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.maze;

import java.util.EnumSet;

/**
 *
 * @author jared
 */
public class Tile {
    int Graphic;
    EnumSet<TileData> data;    
    
    
    public Tile(byte bData) {
        data = TileData.makeSet(bData);
        // TODO: Graphics -- another data file?  Or derived from data?
    }
}
