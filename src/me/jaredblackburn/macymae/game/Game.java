package me.jaredblackburn.macymae.game;

import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import me.jaredblackburn.macymae.ui.Window;
import org.lwjgl.opengl.Display;

/**
 * This class contains the main game loop.
 * 
 * @author Jared Blackburn
 */
public class Game implements IMsgSender, IMsgReciever {
    public static Game game;
    private boolean running = true, paused = false;
    private long  time, lastime;
    private float delta; // TODO: Calculate and use the deltas.
    
    public static void start(Window window) {
        game = new Game();
        game.loop(window);
    }
    

    public void loop(Window window) {
        while(running) {
            window.draw();
            if(running) running = !Display.isCloseRequested();
        }
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    /*                            MESSAGING                                   */
    ////////////////////////////////////////////////////////////////////////////
    
    
    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        MsgQueue.add(new Message(message, this, recipients));
    }

    @Override
    public void recieveMsg(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    /*                              GETTERS                                   */
    ////////////////////////////////////////////////////////////////////////////
    
    
    public float getDelta() {
        return delta;
    }
    
}
