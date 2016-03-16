package me.jaredblackburn.macymae.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumSet;
import me.jaredblackburn.macymae.entity.InputController;
import me.jaredblackburn.macymae.entity.MoveCommand;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import static me.jaredblackburn.macymae.events.MsgType.SHUTDOWN;
import static me.jaredblackburn.macymae.events.MsgType.TOGGLEPAUSE;
import me.jaredblackburn.macymae.game.Game;
import static java.awt.event.KeyEvent.*;



/**
 *
 * @author jared
 */
public class KeyedInput implements IMsgSender, KeyListener {
   private final EnumSet<MoveCommand> steering = EnumSet.noneOf(MoveCommand.class);
   private boolean pauseDown = false;
   public static KeyedInput in;
   
   
   private enum keyCommands {
       UP,
       RIGHT,
       DOWN,
       LEFT,
       START,
       PAUSE,
       EXIT;
   }
   
   
   private KeyedInput() {}
   
   
   public static KeyedInput getKeyedInput() {
       if(in == null) {
           in = new KeyedInput();
       }
       return in;
   }
   
   
   public void update() {
       InputController.userio.update(steering);
   }

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        MsgQueue.add(new Message(message, this, recipients));
    }
    

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        int key = e.getKeyCode();
       steering.clear();
       if((key == KeyEvent.VK_W) || (key == KeyEvent.VK_UP)) {
           steering.add(UP);
       }
       if((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT)) {
           steering.add(RIGHT);
       }
       if((key == KeyEvent.VK_S) || (key == KeyEvent.VK_DOWN)) {
           steering.add(DOWN);
       }
       if((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT)) {
           steering.add(LEFT);
       }
       
       if((key == KeyEvent.VK_ENTER) && !Game.game.getInGame()) {
           Game.game.restart();
       }
       if(key == VK_PAUSE) {
           sendMsg(TOGGLEPAUSE, Game.game);
       } 
       
       if((key == VK_ESCAPE)) {
           sendMsg(SHUTDOWN, Game.game);
       }
   }
    

    // Neither of these should do anything (so they don't)
    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {/*do nothing*/}
    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {/*do nothing*/}
    
}
