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



/**
 *
 * @author jared
 */
public class UserInput {
   public static final UserInput in = new UserInput(); 
   private final EnumSet<MoveCommand> steering = EnumSet.noneOf(MoveCommand.class);
    
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
           steering.add(DOWN);
       }
       if(Keyboard.isKeyDown(KEY_A) || Keyboard.isKeyDown(KEY_LEFT)) {
           steering.add(LEFT);
       }
       InputController.userio.update(steering);
       // TODO: Keys for non-steering (e.g., men) purposes
   }
    
}
