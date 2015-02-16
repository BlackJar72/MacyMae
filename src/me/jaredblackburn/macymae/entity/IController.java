/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.entity;

import me.jaredblackburn.macymae.events.Message;

/**
 *
 * @author jared
 */
public interface IController {
    public void recieveMsg(Message msg);
    public MoveCommand getDirection(Entity owner);    
}
