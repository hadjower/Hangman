package com.hadjower.hangman.view.classes;

import java.io.IOException;

/**
 * Created by Administrator on 03.06.2016.
 */
public enum Category {
    GEOGRAPHY, FOOD, FILM, SPORT, MUSIC, ANIMALS, ALL;

    public static Category valueOf(int i) throws IOException {
        switch (i) {
            case 0:
                return GEOGRAPHY;
            case 1:
                return FOOD;
            case 2:
                return FILM;
            case 3:
                return SPORT;
            case 4:
                return MUSIC;
            case 5:
                return ANIMALS;
            case 6:
                return ALL;
        }
        throw new IOException("Wrong category!");
    }

    public int getNumber() {
        switch (this) {
            case GEOGRAPHY:
                return 0;
            case FOOD:
                return 1;
            case FILM:
                return 2;
            case SPORT:
                return 3;
            case MUSIC:
                return 4;
            case ANIMALS:
                return 5;
            case ALL:
                return 6;
        }
        return -1;
    }
}
