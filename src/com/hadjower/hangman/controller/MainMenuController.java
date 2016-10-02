package com.hadjower.hangman.controller;

import com.hadjower.hangman.model.Connect;
import com.hadjower.hangman.model.Game;
import com.hadjower.hangman.model.Game1Player;
import com.hadjower.hangman.model.Game2PLayers;
import com.hadjower.hangman.view.classes.HangmanStageHolder;
import com.hadjower.hangman.view.classes.Scene;
import com.hadjower.hangman.view.classes.Sound;
import com.hadjower.hangman.view.classes.SoundType;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class MainMenuController {
    @FXML
    private ImageView button1;
    @FXML
    private ImageView button2;

    Map<String, List<Image>> listMap;
    boolean[] isMouseInto = {false, false};


    public void initialize() {
        ControllersManager.setController(this);
        Connect.connect();
        initListMap();
    }

    private void initListMap() {
        listMap = new HashMap<>();
        String path = "/com/hadjower/hangman/assets/pictures/main_menu/";
        List<Image> temp = new ArrayList<>(2);
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                temp.add(new Image(path + i + "Players" + j + ".png"));
            }
            listMap.put("button" + i, new ArrayList<>(temp));
            temp.clear();
        }
    }


    public void imgViewExit(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setImage(listMap.get(imageView.getId()).get(0));
        isMouseInto[getIndex(imageView)] = false;
    }

    private int getIndex(ImageView imageView) {
        return imageView.getId().charAt(6) - 49;
    }

    public void imgViewPressed(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        imageView.setImage(listMap.get(imageView.getId()).get(1));
        isMouseInto[getIndex(imageView)] = true;
    }

    public void imgViewReleased(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        if (isMouseInto[getIndex(imageView)]) {
            imageView.setImage(listMap.get(imageView.getId()).get(0));
            if (imageView.equals(button1)) {
                Game1Player.dropCounters();
                HangmanStageHolder.getInstance().setScene(Scene.CHOOSE_CATEGORY);
            } else {
                HangmanStageHolder.getInstance().setScene(Scene.SET_NAME);
            }
        }
        Sound.playSound(SoundType.SCRATCH);
    }
}
