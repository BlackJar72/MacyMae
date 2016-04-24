package me.jaredblackburn.macymae.entity;

import static me.jaredblackburn.macymae.entity.MoveCommand.NONE;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.game.Difficulty;
import me.jaredblackburn.macymae.game.Game;
import me.jaredblackburn.macymae.maze.MapException;
import me.jaredblackburn.macymae.maze.MapMatrix;
import me.jaredblackburn.macymae.ui.graphics.Graphic;

/**
 *
 * @author Jared Blackburn
 */
public final class Bonus extends Entity {
    private volatile int rank;  // Level of bonus
    private volatile int value; // Points its worth
    private volatile float firstAppear, lastAppear, nextAppear, duration;
    private volatile int which; // Which time, first or second
    private volatile float startTime;
    
    private String graphicName = "bonus";
    
    private static final float BASE_FIRST    = 30f;
    private static final float BASE_LAST     = 90f;
    private static final float BASE_DURATION = 15f;
    private static final int   BASE_VALUE    = 128;
    private static final int   REPEATS       = 2;
    private static final int   NUMBER        = 7;
    
    private volatile float timeToDie;
    private volatile boolean exists;
    
    Bonus(String image, int sx, int sy) throws Exception {
        super(image, sx, sy, 0.3f, Float.MAX_VALUE, 0f, NONE, false, false, 
                new Inanimation());
        graphicName = image;
        startTime = 0;
        reset();
    }
    
    
    @Override
    protected void reset() {
        which = 0;
        coreReset();
    }
    
    
    public void minorReset() {
        which++;
        coreReset();
    
    }
    
    
    private void coreReset() {
        x = (int)sx;
        y = (int)sy;
        dead   = false;
        scared = false;
        exists = false;
        if(which == 0) {
            nextAppear = firstAppear;
        } else {
            nextAppear = lastAppear;
        }
    }
    
    
    @Override
    protected void adjustDifficulty(Difficulty dif) {
        rank  = Math.min(NUMBER - 1, Math.max(0, (dif.ordinal() / REPEATS)));
        value = BASE_VALUE << rank;
        firstAppear = (BASE_FIRST    / (dif.playerVFactor / Difficulty.A.playerVFactor)) + startTime;
        lastAppear  = (BASE_LAST     / (dif.playerVFactor / Difficulty.A.playerVFactor)) + startTime; 
        duration    = (BASE_DURATION / (dif.wispVFactor/ Difficulty.A.wispVFactor));
        graphic     = Graphic.registry.getID(graphicName + rank);
    }
    
    
    @Override
    public void draw() {
        if(exists) {
            super.draw();
        }
    }
    
    
    @Override
    public void update(MapMatrix maze, float time, float delta) throws MapException {
        if(Game.game.isPaused()) {
            return;
        }
        if((time > timeToDie) && exists) {
            minorReset();
        } else if((time >= nextAppear) && !exists && (which < 2)) {
            timeToDie = time + duration;
            exists = true;
        }
    }
    

    @Override
    public void recieveMsg(Message msg) {
        switch(msg.getContent()) {
            //TODO: Message handling hereSTART,
            case START:
                break;
            case CLEARED:
                break;
            case STOP:
                break;
            case CAUGHT:
            case NEXT:
                reset();
                break;
            case POWERED:
                break;
            case BONUS:
                minorReset();
            case GAMEOVER:
                break;
            default:            
        }
    }
    
    
    public int givePoints() {
        return value;
    }
    
    
    public boolean isInPlay() {
        return exists;
    }
    
    
    public void setStartTime(float time) {
        startTime = time;
    }
}
