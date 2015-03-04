package me.jaredblackburn.macymae.game;

import me.jaredblackburn.macymae.entity.InputController;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.ui.UserInput;
import me.jaredblackburn.macymae.ui.Window;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Timer;

/**
 * This class contains the main game loop.
 * 
 * @author Jared Blackburn
 */
public class Game implements IMsgSender, IMsgReciever {
    public static Game game;
    private boolean running = true, paused = false;   
    
    private Timer timer = new Timer();
    private static final float expectedTime = 1f / Window.baseFPS;
    private float lastTime, thisTime;
    private float delta;
    
    
    private Game(){};
    
    
    public static void start(Window window) {
        game = new Game();
        game.timer.reset();
        game.loop(window);
    }
    

    public void loop(Window window) {
        while(running) {
            window.draw();
            if(running) running = !Display.isCloseRequested();
            if(paused) {
                continue;
            }
            updateDelta();
            UserInput.in.update();
            //Entity.updateAll(MapMatrix.getCurrent(), thisTime, delta);
        }
    }
    
    
    private void updateDelta() {
        timer.tick();
        lastTime = thisTime;
        thisTime = timer.getTime();
        delta    = (thisTime - lastTime) / expectedTime;
    }
    
    
    private void pause() {
        timer.pause();
        paused = true;
    }
    
    
    private void unpause() {
        timer.resume();
        paused = false;
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
    
    
    public boolean isPaused() {
        return paused;
    }
    
    
    public Timer getTimer() {
        return timer;
    }
    
    
    public float getTime() {
        return timer.getTime();
    }
    
}
