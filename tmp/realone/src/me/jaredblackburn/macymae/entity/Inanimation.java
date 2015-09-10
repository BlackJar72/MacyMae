/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.entity;

import static me.jaredblackburn.macymae.entity.MoveCommand.NONE;
import me.jaredblackburn.macymae.events.IMsgReciever;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.events.MsgType;
import me.jaredblackburn.macymae.maze.Occupiable;

/**
 *
 * @author jared
 */
public class Inanimation implements IController {    

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        return NONE;
    }

    @Override
    public void reset() {}

    @Override
    public void recieveMsg(Message msg) {}

    @Override
    public void sendMsg(MsgType message, IMsgReciever... recipients) {}
    
}
