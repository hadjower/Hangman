package com.hadjower.hangman.model;

/**
 * Created by Administrator on 09.06.2016.
 */
public class Game2PLayers extends Game{
    private String name;

    Game2PLayers(String name) {
        this.name = name;
    }

    void setWord(String word) {
        this.word = word;
    }

    @Override
    public void start() {
        initHiddenWord();
    }

    public String getName() {
        return name;
    }

}
