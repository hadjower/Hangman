package com.hadjower.hangman.controller;

import com.hadjower.hangman.model.Connect;
import com.hadjower.hangman.model.Player;
import com.hadjower.hangman.model.Pack;
import com.hadjower.hangman.model.TwoPlayersGameManager;
import com.hadjower.hangman.view.classes.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.*;

import static com.hadjower.hangman.view.classes.Scene.MAIN_MENU;

/**
 * Created by Administrator on 09.06.2016.
 */
public class GameTwoController {

    @FXML private Pane pane;
    @FXML private ImageView hangmanImageView;
    @FXML private ImageView player1back;
    @FXML private ImageView player2back;
    @FXML private Label wordLabel;
    @FXML private Pane buttonsPane;
    @FXML private Pane popUpPane;
    @FXML private Label popUpLabel;
    @FXML private ImageView backNewBtn;
    @FXML private ImageView mainMenuBtn;
    @FXML private ImageView soundOn;
    @FXML private ImageView popUpPlayerBack;
    @FXML private Label popUpPlayerName;
    @FXML private Label smallCount;
    @FXML private Label bigCount;
    @FXML private Label player1name;
    @FXML private Label player2name;


    private List<Image> hangmanImages;    //images of Hangman for different count of mistakes
    public static final int HANGMAN_COUNTER = 10;
    private Map<String, ImageView> imageViewMap; //Stores imagesView, that imitate buttons. Access is provided by name of ImageView
    private Map<String, List<Image>> imageListMap;  //Stores lists of images for animations of ImageViews' click
    private List<Button> buttons;
    private Map<String, Image> background;

    public void initialize() {
        initButtons();
        initImages();
        updateCounter();
        initImageViewMap();
        initImageListMap();
        putImages();

        ControllersManager.setController(this);

        //todo лэйбл игрока в popup
    }

    private void initImageListMap() {
        try {
            imageListMap = Connect.getImageMap();
            background = Connect.getRedBlue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void putImages() {
        ImageView imageView;
        List<String> imgViewIDs = Arrays.asList("soundOn", "mainMenuBtn", "backNewBtn");
        for (String imgViewID : imgViewIDs) {
            imageView = imageViewMap.get(imgViewID);
            imageView.setImage(imageListMap.get(imgViewID).get(0));
        }
        player1back.setImage(background.get(TwoPlayersGameManager.getInstance().isPlayer1Turn() ? "blue" : "red") );
        popUpPlayerBack.setImage(background.get(TwoPlayersGameManager.getInstance().isPlayer1Turn() ? "blue" : "red") );
        player2back.setImage(background.get(TwoPlayersGameManager.getInstance().isPlayer1Turn() ? "red" : "blue") );
        player1name.setText(TwoPlayersGameManager.getInstance().isPlayer1Turn() ? TwoPlayersGameManager.getInstance().getP1name() : TwoPlayersGameManager.getInstance().getP2name());
        popUpPlayerName.setText(TwoPlayersGameManager.getInstance().isPlayer1Turn() ? TwoPlayersGameManager.getInstance().getP1name() : TwoPlayersGameManager.getInstance().getP2name());
        player2name.setText(TwoPlayersGameManager.getInstance().isPlayer1Turn() ? TwoPlayersGameManager.getInstance().getP2name() : TwoPlayersGameManager.getInstance().getP1name());
    }

    private void initImageViewMap() {
        imageViewMap = new HashMap<>();
        imageViewMap.put("soundOn", soundOn);
        imageViewMap.put("mainMenuBtn", mainMenuBtn);
        imageViewMap.put("backNewBtn", backNewBtn);
    }

    private void updateCounter() {
        bigCount.setText(String.valueOf(TwoPlayersGameManager.getInstance().getCurrGameWins()));
        smallCount.setText(String.valueOf(TwoPlayersGameManager.getInstance().getOtherGameWins()));
    }

    private void initImages() {
        hangmanImages = new ArrayList<>(HANGMAN_COUNTER);
        String path = "/com/hadjower/hangman/assets/pictures/round/Hangman0";
        for (int i = 0; i < HANGMAN_COUNTER; i++) {
            hangmanImages.add(new Image(path + i + ".png"));
        }
    }

    private void initButtons() {
        buttons = new ArrayList<>(33);
        int startX = 10;
        int startY = 35;
        boolean passedZh = false;
        char c = 'А';
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 4 && (j == 0 || j == 6))
                    continue;
                KeyButton b;
                if (c != 'Ж' || passedZh) {
                    b = new KeyButton(String.valueOf(c));
                    c++;
                } else {
                    b = new KeyButton(String.valueOf('Ё'));
                    passedZh = true;
                }
                b.setLayoutX(startX + j * 85);
                b.setLayoutY(startY + i * 65);
                b.setPrefWidth(60);
                b.setPrefHeight(60);
                b.getStyleClass().addAll("buttonOff", "keyButton", "buttonSize");
                b.setFont(Font.font("TagirCTT", 30));

                b.setOnMouseClicked(this::keyClicked);

                buttons.add(b);
            }
        }
        buttonsPane.getChildren().addAll(buttons);
    }

    private void keyClicked(MouseEvent event) {
        KeyButton b = (KeyButton) event.getSource();
        if (b.wasClicked())
            return;
        Sound.playSound(SoundType.SCRATCH);
        String s = b.getText().toLowerCase();
        Pack pack = TwoPlayersGameManager.getInstance().getPack(s);

        b.getStyleClass().remove("buttonOff");
        b.getStyleClass().add(pack.isGuessed() ? "buttonGuessed" : "buttonMismatched");

        guessProcess(pack);

        b.setClicked();
    }

    private void guessProcess(Pack pack) {
        wordLabel.setText(pack.getHiddenWord());
        if (pack.isFinished()) {
            finishGame(pack.isWin());
        } else {
            if (!pack.isGuessed()) {
                hangmanImageView.setImage(hangmanImages.get(TwoPlayersGameManager.getInstance().getHangmanCounter()));
            }
        }
    }

    private void finishGame(boolean isWin) {
        if (isWin)
            TwoPlayersGameManager.getInstance().countPlus();
        popUpLabel.setText(isWin ? "победа!" : "неудача");
        hangmanImageView.setImage(new Image("/com/hadjower/hangman/assets/pictures/round/" + (isWin ? "faceWon.png" : "faceLost.png")));
        Sound.playSound(isWin ? SoundType.WIN : SoundType.LOSE);
        buttonsPane.setVisible(false);
        popUpPane.setVisible(true);
        backNewBtn.setImage(imageListMap.get("newBtn").get(0));
        Player.increaseCounter(isWin);
        wordLabel.setText(TwoPlayersGameManager.getInstance().getWord());
    }

    public void startGame() {
        initSceneEvent();
        wordLabel.setText(TwoPlayersGameManager.getInstance().getHiddenWord());
    }

    //todo придумать куда засунуть инициализацию ивента
    private void initSceneEvent() {
        pane.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                pausePanel();
            }
        });
    }

    public void imgViewExit(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        if (imageView.equals(backNewBtn) && TwoPlayersGameManager.getInstance().isFinished()) {
            imageView.setImage(imageListMap.get("newBtn").get(0));
        } else if (imageView.equals(soundOn)) {
            imageView.setImage(imageListMap.get(Sound.isOn() ? "soundOn" : "soundOff").get(0));// todo исправить подгрузку картинок
        } else {
            imageView.setImage(imageListMap.get(imageView.getId()).get(0));
        }
    }

    public void imgViewPressed(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        if (imageView.equals(backNewBtn) && TwoPlayersGameManager.getInstance().isFinished()) {
            imageView.setImage(imageListMap.get("newBtn").get(1));
        } else if (imageView.equals(soundOn)) {
            imageView.setImage(imageListMap.get(Sound.isOn() ? "soundOn" : "soundOff").get(1));
        } else {
            imageView.setImage(imageListMap.get(imageView.getId()).get(1));
        }
    }

    public void imgViewReleased(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        if (isMouseInto((MouseEvent) event, imageView)) {
            if (imageView.equals(backNewBtn)) {
                if (TwoPlayersGameManager.getInstance().isFinished()) {
                    imageView.setImage(imageListMap.get("newBtn").get(0));
                    ControllersManager.beginTwoPlayerGame(TwoPlayersGameManager.getInstance().getP1name(), TwoPlayersGameManager.getInstance().getP2name());
                } else {
                    pausePanel();
                }
            } else if (imageView.equals(soundOn)) {
                imageView.setImage(imageListMap.get(Sound.isOn() ? "soundOff" : "soundOn").get(0));
                Sound.turnSound();
            } else {
                HangmanStageHolder.getInstance().setScene(MAIN_MENU);
                imageView.setImage(imageListMap.get(imageView.getId()).get(0));

            }
            Sound.playSound(SoundType.SCRATCH);
        }
    }

    private void pausePanel() {
        if (popUpPane.isVisible()) {
            popUpPane.setVisible(false);
            buttonsPane.setVisible(true);
        } else {
            popUpPane.setVisible(true);
            buttonsPane.setVisible(false);
        }
    }

    private boolean isMouseInto(MouseEvent event, ImageView imageView) {
        double x = 0, y = 0;
        if (imageView.getParent().equals(popUpPane)) {
            x = popUpPane.getLayoutX();
            y = popUpPane.getLayoutY();
        }
        return event.getSceneX() >= imageView.getLayoutX() + x
                && event.getSceneX() <= imageView.getLayoutX() + x + imageView.getFitWidth()
                && event.getSceneY() >= imageView.getLayoutY() + y
                && event.getSceneY() <= imageView.getLayoutY() + y + imageView.getFitHeight();
    }
}
