package me.jaredblackburn.macymae.entity;

/**
 * This class represents direction an entity may try to move; this encodes 
 * player input with a level of abstraction, and is also used by entity AI
 * to represent the select move direction.
 * 
 * @author Jared Blackburn
 */
public enum MoveCommand {
    // Direction data start at the top and moves clockwise 90 degrees at a time
    UP,
    RIGHT,
    DOWN,
    LEFT,
    NONE;
    
}
