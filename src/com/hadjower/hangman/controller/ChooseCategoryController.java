package com.hadjower.hangman.controller;

import com.hadjower.hangman.view.classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 02.06.2016.
 */
public class ChooseCategoryController{

    @FXML private Button allBtn;
    @FXML private Button animalsBtn;
    @FXML private Button musicBtn;
    @FXML private Button sportBtn;
    @FXML private Button filmsBtn;
    @FXML private Button foodBtn;
    @FXML private Button geographyBtn;

    private List<Button> buttons;

    public void categoryClick(ActionEvent actionEvent) {
        try {
            ControllersManager.beginOnePlayerGame(getCategory(actionEvent));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sound.playSound(SoundType.SCRATCH);
    }

    private Category getCategory(ActionEvent actionEvent) throws IOException {
        for (int i = 0; i < 7; i++) {
            if (((Button)actionEvent.getSource()).equals(buttons.get(i))) {
                return Category.valueOf(i);
            }
        }
        throw new IOException("wrong category");
    }

    public void initialize() {
        ControllersManager.setController(this);
        buttons = Arrays.asList(geographyBtn, foodBtn, filmsBtn, sportBtn, musicBtn, animalsBtn, allBtn);
    }
}
