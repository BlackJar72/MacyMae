package me.jaredblackburn.macymae.game;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import static me.jaredblackburn.macymae.events.MsgType.CAUGHT;
import static me.jaredblackburn.macymae.events.MsgType.NEXT;
import static me.jaredblackburn.macymae.events.MsgType.TMPPAUSE;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.maze.MapMatrix.DotCenter;
import me.jaredblackburn.macymae.maze.Tile;
import me.jaredblackburn.macymae.ui.Toast;
import me.jaredblackburn.macymae.ui.KeyedInput;
import me.jaredblackburn.macymae.ui.SwingWindow;

/**
 * This class contains the main game loop.
 * 
 * @author Jared Blackburn
 */
public class Game implements IMsgSender, IMsgReciever, Runnable {

    public volatile static Game game;
    public volatile static Player player; // should do this better
    private volatile boolean running = true,   paused = false, 
                             inGame  = false, isGameOver = false,
                             inDemo  = false;
    
    private volatile float gameOverTime = -1f;
    
    private int level;
    private Difficulty difficulty;
    
    private boolean tmpPause = false;
    private float tmpPauseTime = 0f;
    
    private final Timer timer = new Timer();
    private static final float expectedTime  = 1f / SwingWindow.baseFPS;
    private static final long  expectedSleep = 1000 / SwingWindow.baseFPS;
    private float lastTime, thisTime, passedTime;
    private float delta;
    private DotCenter dotCenter;
    
    private Thread runner;
    private SwingWindow window;
    
    public static final Random random = new Random();
      

    public class Timer {
        private final long base;
        private long current, previous, elapsed;
        private float out;
        private boolean running;
        public Timer() {
            current = previous = base = System.nanoTime();
            running = true;
            elapsed = 0;
        }
        public void reset() {
            current = previous = base;
            elapsed = 0;
        }
        public void pause() {
            running = false;
        }
        public void resume() {
            running = true;
        }
        public void tick() {
            previous = current;
            current = System.nanoTime();
            if(running) {
                elapsed = current - base;
            } 
        }
        public float getTime() {
           if(elapsed <= 0) {
               elapsed = 1;
           }
           out = (float)(((double)(elapsed)) / 1000000000d);
           return out;
        }
    }
    
    
    private Game(){
        init(); 
    };
    
    
    private void init() {
        player = new Player();
        level  = 0;
        difficulty = Difficulty.get(level);
        Entity.setDifficulty(difficulty);
        MapMatrix.setCurrent(level);
        dotCenter = MapMatrix.getCurrentDotCenter(); 
    }
    
    
    public static Game getGame() {
        if(game == null) {
            game = new Game();
        }
        return game;
    }
    
    
    public void start(SwingWindow window) {
        this.window = window;
        game = new Game();        
        game.timer.reset();
        window.endGame();
        run();
    }
    
    
    public void restart() {
        init();
        Entity.setDemo(false);
        Entity.resetAll();
        startTmpPause(0.5f);
        inGame = true;
        inDemo = false;
        paused = false;
        isGameOver = false;
        gameOverTime = -1f;
        SwingWindow.getWindow().startGame();
    }
    
    
    public void startDemo() {
        init();
        Entity.setDemo(true);
        Entity.resetAll();
        startTmpPause(0.5f);
        inGame = false;
        inDemo = true;
        paused = false;
        isGameOver = false;
        gameOverTime = -1f;
        SwingWindow.getWindow().startGame();
    }
    

    public void loop(SwingWindow window) {
        while(running) {
            window.draw();
            updateDelta();
            KeyedInput.in.update();
            Toast.update();
            if(tmpPause && (inGame || inDemo)) {
                doTmpPause();
            } else {
                if((inGame || inDemo) && !paused) {
                    Entity.updateAll(MapMatrix.getCurrent(), thisTime, delta);
                }
                MsgQueue.deliver();
            }
            if(isGameOver) {
                gameOver();
            }
            gameSleep();
        }
    }
    
    
    private void updateDelta() {
        timer.tick();
        lastTime   = thisTime;
        thisTime   = timer.getTime();
        passedTime = (thisTime - lastTime);
        delta      =  passedTime / expectedTime;
        //System.out.println("FPS = " + Window.baseFPS / delta);
    }
    
    
    private void pause() {
        timer.pause();
        paused = true;
    }
    
    
    private void unpause() {
        timer.resume();
        paused = false;
    }
    
    
    private void togglePause() {
        paused = !paused;
    }
    
    
    private void startTmpPause(float duration) {
        tmpPause = true;
        tmpPauseTime = duration;
    }
    
    
    private void doTmpPause() {
        tmpPauseTime -= passedTime;
        if(tmpPauseTime <= 0f) {
            tmpPauseTime = 0f;
            tmpPause = false;
        }
    }
    
    
    private void newBoard() {
        level++;
        difficulty = Difficulty.get(level);
        Entity.setDifficulty(difficulty);
        MapMatrix.setCurrent(level);
        dotCenter = MapMatrix.getCurrentDotCenter();
        Toast.unset();
    }
    
    
    private void endGame() {
        inGame   = false;
        inDemo   = false;
        isGameOver = true;
        gameOverTime = thisTime + 30f;
    }
    
    
    private void gameOver() {
        if(gameOverTime <= thisTime) {
            gameOverTime = -1;
            isGameOver = false;
            Toast.unset();
            SwingWindow.getWindow().endGame();
        }
    }
    
    
    private void gameSleep() {
        try {
            long time = Math.max(expectedSleep - (long)(timer.getTime() * 1000), 
                    10);
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void run() {        
        game.loop(window);
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
        //System.out.println(message + " recieved from " + msg.getSender() + " at " + getTime());
        switch(message) {
            case START:
                break;
            case CLEARED:               
                startTmpPause(0.5f);
                sendMsg(NEXT, this);
                //running = false; // Sand-in, for now
                break;
            case NEXT:
                Entity.resetAll();
                newBoard();
                sendMsg(TMPPAUSE, this);
                break;
            case CAUGHT:                
                startTmpPause(0.5f);
                sendMsg(CAUGHT,  player, Entity.macy,
                        Entity.wisp1, Entity.wisp2, Entity.wisp3, Entity.wisp4);
                sendMsg(TMPPAUSE, this);
                break;
            case TMPPAUSE:
                startTmpPause(0.5f);
                break;
            case TOGGLEPAUSE:
                if(inGame) {
                    togglePause();
                }
                break;
            case GAMEOVER:
                endGame();
                break;
            case SHUTDOWN:
                running = false;
                break;
            default:
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
        return paused || tmpPause;
    }
    
    
    public Timer getTimer() {
        return timer;
    }
    
    
    public float getTime() {
        return thisTime;
    }
    
    public DotCenter getDotCenter() {
        return dotCenter;
    }    
    
    public Tile findCenter() {
        return dotCenter.getTile();
    }
    
    
    public boolean getInGame() {
        return inGame;
    }
    
    
    public int getLevel() {
        // For player, not programer
        return level + 1;
    }
    
    
    public boolean getIsGameOver() {
        return isGameOver;
    }
    
    
    public boolean getIsDemo() {
        return inDemo;
    }
    
}
