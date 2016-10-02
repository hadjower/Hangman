package com.hadjower.hangman.model;

import com.hadjower.hangman.view.classes.Category;

/**
 * Created by Administrator on 09.06.2016.
 */
public class Game1Player extends Game{
    private Category category;
    private boolean isHelpUsed;

    private static int wins;
    private static int defeats;

    public Game1Player(Category category) {
        this.category = category;
        isHelpUsed = false;
        hangmanCounter = 0;
    }

    public void start() {
        word = getRandomWord();
        initHiddenWord();
    }

    private String getRandomWord() {
        return Connect.getRandomWord(category).toLowerCase();
    }

    public Pack useHelp() {
        isHelpUsed = true;
        int index;
        do {
            index = (int) (Math.random()*hiddenWord.length());
        } while (hiddenWord.charAt(index) != '_');
        String s = String.valueOf(word.charAt(index));
        editHiddenWord(s);
        return new Pack(true, isHanged(), isWordGuessed(), hiddenWord.toString());
    }

    public boolean isHelpUsed() {
        return isHelpUsed;
    }
    public Category getCategory() {
        return category;
    }

    public static void increaseCounter(boolean isWin) {
        if (isWin) {
            wins++;
        } else {
            defeats++;
        }
    }

    public static int getWins() {
        return wins;
    }

    public static int getDefeats() {
        return defeats;
    }

    public static void dropCounters() {
        defeats = 0;
        wins = 0;
    }
}
