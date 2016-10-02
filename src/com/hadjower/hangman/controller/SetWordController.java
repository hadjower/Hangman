package com.hadjower.hangman.controller;

import com.hadjower.hangman.model.Connect;
import com.hadjower.hangman.model.TwoPlayersGameManager;
import com.hadjower.hangman.view.classes.Sound;
import com.hadjower.hangman.view.classes.SoundType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 10.06.2016.
 */
public class SetWordController {

    @FXML
    private Button nextBtn;
    @FXML
    private Button randBtn;
    @FXML
    private TextField textBox;
    @FXML
    private Label playerName;
    @FXML
    private ImageView playerBack;

    private HashMap<String, List<Image>> map;
    private HashMap<String, Image> background;

    public void initialize() {
        ControllersManager.setController(this);
        try {
            map = Connect.getImageMap();
            background = Connect.getRedBlue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        playerName.setText(TwoPlayersGameManager.getInstance().getName());
        playerBack.setImage(TwoPlayersGameManager.getInstance().isPlayer1Turn() ? background.get("blue") : background.get("red"));

        textBox.textProperty().addListener((observable, oldValue, newValue) -> {
            textChanged(oldValue);
        });
    }

    private void textChanged(String oldValue) {
        if (textBox.getText().trim().length() < 1) {
            ((ImageInput)nextBtn.getEffect()).setSource(map.get("next").get(0));
        } else if (oldValue.trim().length() == 0 && Pattern.matches("[а-яА-Я -]+", textBox.getText())){
            ((ImageInput)nextBtn.getEffect()).setSource(map.get("next").get(1));
        }
    }

    public void next(ActionEvent actionEvent) {
        if (textBox.getText().trim().length() > 0 && Pattern.matches("[а-яА-Я -]+", textBox.getText())) {
            ControllersManager.continueTwoPlayerGame(textBox.getText().trim());
            Sound.playSound(SoundType.SCRATCH);
        }
    }

    public void random(ActionEvent actionEvent) {
        String word = Connect.getRandomWord();
        textBox.setText(word);
        Sound.playSound(SoundType.SCRATCH);
    }

}
