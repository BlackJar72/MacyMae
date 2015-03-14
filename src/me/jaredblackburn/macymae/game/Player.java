package me.jaredblackburn.macymae.game;

import me.jaredblackburn.macymae.entity.Entity;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import static me.jaredblackburn.macymae.events.MsgType.GAMEOVER;

/**
 *
 * @author Jared Blackburn
 */
public class Player implements IMsgReciever, IMsgSender {
    private int score;
    private int lives;
    private int wispCount;
    
    
    public Player() {
        score = 0;
        lives = 3;
        wispCount = 0;
    }

    @Override
    public void recieveMsg(Message msg) {
        MsgType message = msg.getContent();
        switch(message) {
            case START:
                break;
            case CLEARED:
                break;
            case NEXT:
                break;
            case STOP:
                break;
            case CAUGHT:
                lives--;
                if(lives < 1) {
                    sendMsg(GAMEOVER, Game.game);
                }
                System.out.println("Lives:" + lives);
                break;
            case POWERED:
                wispCount = 0;
                break;
            case WDIE:
                incrementScore(100 << wispCount++);
                break;
            case GAMEOVER:
                break;
            default:
                return;            
        }
    }
    
    
    public void incrementScore(int value) {
        score += value;
    }
    
    
    public int getScore() {
        return score;
    }
    
    
    public int getLives() {
        return lives;
    }
    

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        MsgQueue.add(new Message(message, this, recipients));
    }
}
