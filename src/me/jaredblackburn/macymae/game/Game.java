package me.jaredblackburn.macymae.game;

import java.util.Random;
import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.entity.InputController;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.MapMatrix.DotCenter;
import me.jaredblackburn.macymae.maze.Tile;
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
    public static Player player; // should do this better
    private boolean running = true, paused = false;   
    
    private Timer timer = new Timer();
    private static final float expectedTime = 1f / Window.baseFPS;
    private float lastTime, thisTime, passedTime;
    private float delta;
    private DotCenter dotCenter = MapMatrix.getCurrentDotCenter();
    
    public static final Random random = new Random();
    
    
    private Game(){
        player = new Player();
    };
    
    
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
            Entity.updateAll(MapMatrix.getCurrent(), thisTime, delta);
            MsgQueue.deliver();
        }
        System.out.println("Final score: " + player.getScore());
    }
    
    
    private void updateDelta() {
        timer.tick();
        lastTime   = thisTime;
        thisTime   = timer.getTime();
        passedTime = (thisTime - lastTime);
        delta      =  passedTime / expectedTime;
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
        MsgType message = msg.getContent();
        switch(message) {
            case START:
                break;
            case CLEARED:
                running = false; // Sand-in, for now
                break;
            case NEXT:
                break;
            case STOP:
                break;
            case CAUGHT:
                break;
            case POWERED:
                break;
            case PAUSE:
                break;
            case UNPAUSE:
                break;
            case GAMEOVER:
                running = false; // Sand-in, for now
                break;
            default:
                throw new AssertionError(message.name());
        }
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    /*                              GETTERS                                   */
    ////////////////////////////////////////////////////////////////////////////
    
    
    public float getDelta() {
        return delta;
    }
    
    
    public float getPassedTime() {
        return passedTime;
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
    
    public DotCenter getDotCenter() {
        return dotCenter;
    }    
    
    public Tile findCenter() {
        return dotCenter.getTile();
    }
    
}
