package com.hadjower.hangman.model;

/**
 * Created by Administrator on 04.06.2016.
 */
public class Pack {
    private boolean isGuessed;
    private boolean isFinished;
    private boolean isWin;
    private String hiddenWord;

    public Pack(boolean isGuessed, boolean isHanged, boolean isWordGuessed, String hiddenWord) {
        this.isGuessed = isGuessed;
        this.isFinished = isHanged || isWordGuessed;
        if (isFinished) {
            this.isWin = isWordGuessed;
        }
        this.hiddenWord = hiddenWord;
    }

    public boolean isGuessed() {
        return isGuessed;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isWin() {
        return isWin;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }
}
