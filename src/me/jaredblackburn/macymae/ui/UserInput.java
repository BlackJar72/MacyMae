package me.jaredblackburn.macymae.ui;

import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.jaredblackburn.macymae.entity.InputController;
import me.jaredblackburn.macymae.entity.MoveCommand;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.*;
import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.IMsgSender;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgQueue;
import me.jaredblackburn.macymae.events.MsgType;
import static me.jaredblackburn.macymae.events.MsgType.SHUTDOWN;
import static me.jaredblackburn.macymae.events.MsgType.TOGGLEPAUSE;
import me.jaredblackburn.macymae.game.Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.*;



/**
 *
 * @author jared
 */
public class UserInput implements IMsgSender {
   public static final UserInput in = new UserInput(); 
   private final EnumSet<MoveCommand> steering = EnumSet.noneOf(MoveCommand.class);
   private boolean pauseDown = false;
    
   private UserInput() {}
   
   
   public static void init() {
       try {
           Keyboard.create();
       } catch (LWJGLException ex) {
           Logger.getLogger(UserInput.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   
   public static void cleanup() {
       Keyboard.destroy();
   }
   
   
   public void update() {
       steering.clear();
       if(Keyboard.isKeyDown(KEY_W) || Keyboard.isKeyDown(KEY_UP)) {
           steering.add(UP);
       }
       if(Keyboard.isKeyDown(KEY_D) || Keyboard.isKeyDown(KEY_RIGHT)) {
           steering.add(RIGHT);
       }
       if(Keyboard.isKeyDown(KEY_S) || Keyboard.isKeyDown(KEY_DOWN)) {
           steering.add(DOWN);;
       }
       if(Keyboard.isKeyDown(KEY_A) || Keyboard.isKeyDown(KEY_LEFT)) {
           steering.add(LEFT);
       }
       InputController.userio.update(steering);
       // TODO: Keys for non-steering (e.g., men) purposes
       if(Keyboard.isKeyDown(KEY_RETURN) && !Game.game.getInGame()) {
           Game.game.restart();
       }
       if(Keyboard.isKeyDown(KEY_PAUSE)) {
           pauseDown = true;
       } 
       if(Keyboard.isKeyDown(KEY_ESCAPE)) {
           if(Keyboard.isKeyDown(KEY_RSHIFT) 
                   || Keyboard.isKeyDown(KEY_LSHIFT)
                   || Keyboard.isKeyDown(KEY_RCONTROL) 
                   || Keyboard.isKeyDown(KEY_LSHIFT)) {
               sendMsg(SHUTDOWN, Game.game);
       } else {
               pauseDown = true;
           }
       }
       if(pauseDown && !(Keyboard.isKeyDown(KEY_PAUSE) 
                      || Keyboard.isKeyDown(KEY_ESCAPE))) {
           pauseDown = false;
           sendMsg(TOGGLEPAUSE, Game.game);
       }
   }

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {
        MsgQueue.add(new Message(message, this, recipients));
    }
    
}
