/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.entity;

import static me.jaredblackburn.macymae.entity.MoveCommand.*;
import me.jaredblackburn.macymae.events.Message;
import me.jaredblackburn.macymae.maze.Occupiable;

/**
 *
 * @author jared
 */
public class EnemyAI implements IController {

    @Override
    public MoveCommand getDirection(Occupiable loc) {
        return NONE;
    }

    @Override
    public void recieveMsg(Message msg) {}
    
}
