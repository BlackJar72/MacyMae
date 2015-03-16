/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.jaredblackburn.macymae.game;

/**
 *
 * @author jared
 */
public enum Difficulty {
    A (1.00f, 1.00f, 10f, -5f),
    B (1.25f, 1.25f,  9f, -4f),
    C (1.50f, 1.50f,  8f, -3f),
    D (1.75f, 1.75f,  7f, -2f),
    E (2.00f, 2.00f,  6f, -1f),
    G (1.30f, 1.55f,  8f, -4f),
    H (1.55f, 1.80f,  7f, -3f),
    I (1.80f, 2.05f,  5f, -2f),
    J (2.05f, 2.30f,  8f, -1f),
    M (1.60f, 2.10f,  5f, -3f),
    N (1.85f, 2.35f,  4f, -2f),
    O (2.10f, 2.60f,  3f, -1f),
    S (1.90f, 2.65f,  4f, -2f),
    T (2.15f, 2.90f,  3f, -1f),
    U (2.00f, 3.00f,  2f, -5f),
    V (2.00f, 3.25f,  2f, -4f),
    W (2.00f, 3.50f,  2f, -3f),
    X (2.00f, 3.75f,  1f, -2f),
    Y (2.00f, 4.00f,  1f, -1f),
    Z (2.00f, 5.00f,  0f,  0f);
    
    public final float playerVFactor;
    public final float wispVFactor;
    public final float powerTime;
    public final float coolDown;
    
    private static final Difficulty[] values = Difficulty.values();
    
    Difficulty(float ps, float ws, float pt, float cd) {
        playerVFactor = ps;
        wispVFactor   = ws;
        powerTime     = pt;
        coolDown      = cd;
    }
    
    
    public static Difficulty get(int level) {
        if(level >= values.length) {
            level = values.length - 1;
        }
        return values[level];
    }
    
}
