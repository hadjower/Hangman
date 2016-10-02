package com.hadjower.hangman.controller;

import com.hadjower.hangman.model.Connect;
import com.hadjower.hangman.view.classes.Sound;
import com.hadjower.hangman.view.classes.SoundType;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 10.06.2016.
 */
public class SetNamesController {
    @FXML
    private ImageView continueIV;
    @FXML
    private TextField textBox1;
    @FXML
    private TextField textBox2;

    private HashMap<String, List<Image>> next;
    private boolean isInside;


    public void initialize() {
        ControllersManager.setController(this);
        try {
            next = Connect.getImageMap();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void imgViewReleased(Event event) {
        if (isInside) {
            continueIV.setImage(next.get("next").get(1));
            Sound.playSound(SoundType.SCRATCH);
            String name1 = Pattern.matches("[a-zA-Zа-яА-Я0-9]+", textBox1.getText()) ? textBox1.getText() : "Игрок 1";
            String name2 = Pattern.matches("[a-zA-Zа-яА-Я0-9]+", textBox2.getText()) ? textBox2.getText() : "Игрок 2";
            ControllersManager.beginTwoPlayerGame(name1, name2);
        }
    }

    public void imgViewExit(Event event) {
        isInside = false;
        continueIV.setImage(next.get("next").get(1));
    }

    public void imgViewPressed(Event event) {
        isInside = true;
        continueIV.setImage(next.get("next").get(2));
    }

    public void keyTyped(Event event) {
        TextField source = (TextField) event.getSource();
        if (source.getText().trim().length() > 11)
            source.deleteText(source.getText().length() - 1, source.getText().length());
    }
}
