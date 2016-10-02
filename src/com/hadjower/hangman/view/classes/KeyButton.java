package com.hadjower.hangman.view.classes;

import javafx.scene.control.Button;

/**
 * Created by Administrator on 04.06.2016.
 */
public class KeyButton extends Button {
    private boolean wasClicked;

    public KeyButton(String text) {
        super(text);
        wasClicked = false;
    }

    public boolean wasClicked() {
        return wasClicked;
    }

    public void setClicked() {
        wasClicked = true;
    }
}
