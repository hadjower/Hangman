package com.hadjower.hangman.model;

import java.util.ArrayList;
import java.util.List;

import static com.hadjower.hangman.controller.GameOneController.HANGMAN_COUNTER;

/**
 * Created by Administrator on 03.06.2016.
 */
public abstract class Game {
    protected String word;
    protected StringBuilder hiddenWord;
    protected int hangmanCounter;



    public Game(){}

    public abstract void start();


    public Pack sendLetterAndGetPackage(String s) {
        boolean isGuessed;
        if (!word.contains(s)) {
            hangmanCounter++;
            isGuessed = false;
        } else {
            editHiddenWord(s);
            isGuessed = true;
        }
        return new Pack(isGuessed, isHanged(), isWordGuessed(), hiddenWord.toString());
    }


    protected void editHiddenWord(String s) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            int index = word.indexOf(s, i);
            if (index == -1)
                break;
            i = index;
            positions.add(index);
        }

        StringBuilder tempWord = new StringBuilder(hiddenWord);
        hiddenWord.setLength(0);
        for (int i = 0; i < tempWord.length(); i++) {
            if (tempWord.charAt(i) == '_') {
                if (positions.contains(i)) {
                    hiddenWord.append(s);
                } else if (word.charAt(i) == ' ') {
                    hiddenWord.append(' ');
                } else {
                    hiddenWord.append('_');
                }
            } else {
                hiddenWord.append(tempWord.charAt(i));
            }
        }
    }


    public boolean isWordGuessed() {
        return !hiddenWord.toString().contains("_");
    }

    public String getWord() {
        return word;
    }

    public int getHangmanCounter() {
        return hangmanCounter;
    }

    public boolean isHanged() {
        return hangmanCounter == HANGMAN_COUNTER - 1;
    }

    protected void initHiddenWord() {
        hiddenWord = new StringBuilder("");
        for (int i = 0; i < word.length(); i++) {
            Character c = word.charAt(i);
            if (c == ' ') {
                hiddenWord.append('\n');
            } else if(Character.isLetter(c)) {
                hiddenWord.append('_');
            } else {
                hiddenWord.append(c);
            }
        }
    }

    public String getHiddenWord() {
        return hiddenWord.toString();
    }

}
