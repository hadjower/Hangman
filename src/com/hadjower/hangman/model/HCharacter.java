package com.hadjower.hangman.model;

/**
 * Created by Administrator on 03.06.2016.
 */
public class HCharacter {
    private char c;
    private int position;

    public HCharacter(String s, int position) {
        c = s.charAt(0);
        this.position = position;
    }
}
