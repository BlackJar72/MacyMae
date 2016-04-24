package me.jaredblackburn.macymae.game;

import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import static me.jaredblackburn.macymae.events.MsgType.GAMEOVER;
import me.jaredblackburn.macymae.ui.Toast;

/**
 *
 * @author Jared Blackburn
 */
public class Player implements IMsgReciever, IMsgSender {
    private static final int BASE_LIFE_POINTS = 16000;
    private int lifePoints;
    private int lifeLevel;
    
    private int score;
    private int lives;
    private int wispCount;
    
    private int points;
    
    
    public Player() {
        score      = 0;
        lives      = 3;
        lifeLevel  = 0;
        lifePoints = BASE_LIFE_POINTS;
        wispCount  = 0;
    }

    
    @Override
    public void recieveMsg(Message msg) {
        MsgType message = msg.getContent();
        switch(message) {
            case CAUGHT:
                lives--;
                if(lives < 1) {
                    sendMsg(GAMEOVER, Game.game);
                }
                break;
            case POWERED:
                wispCount = 0;
                break;
            case WDIE:
                points = 250 << wispCount++;
                incrementScore(points);
                Toast.set("+" + points);
                break;
            case BONUS:
                points = Entity.bonus.givePoints();
                incrementScore(points);
                Toast.set("+" + points);
                break;
            default:
                return;            
        }
    }
    
    
    public void incrementScore(int value) {
        score += value;
        if(score >= lifePoints) {
            extraLife();
        }
    }
    
    
    public int getScore() {
        return score;
    }
    
    
    public void extraLife() {
        lives++;
        lifeLevel++;
        lifePoints = BASE_LIFE_POINTS << lifeLevel;
        Toast.set("Extra Life");
    }
    
    
    public int getLives() {
        return lives;
    }
    

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        MsgQueue.add(new Message(message, this, recipients));
    }
}
