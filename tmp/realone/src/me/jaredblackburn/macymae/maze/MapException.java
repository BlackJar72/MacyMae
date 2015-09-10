package me.jaredblackburn.macymae.maze;

/**
 *
 * @author jared
 */
public class MapException extends Exception {
    public final String msg;
    
    public MapException() {
        msg = "ERROR in map";
    }
    
    
    public MapException(String msg) {
        this.msg = msg;
        System.err.println(msg);
    }

}
