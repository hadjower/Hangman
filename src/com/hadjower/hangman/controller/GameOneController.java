package com.hadjower.hangman.controller;

import com.hadjower.hangman.model.Game1Player;
import com.hadjower.hangman.model.Pack;
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

import java.io.IOException;
import java.util.*;

import static com.hadjower.hangman.view.classes.Scene.CHOOSE_CATEGORY;
import static com.hadjower.hangman.view.classes.Scene.MAIN_MENU;

/**
 * Created by Administrator on 01.06.2016.
 */
public class GameOneController {
    @FXML
    private Pane popUpPane;
    @FXML
    private ImageView soundBtn;
    @FXML
    private ImageView mainMenuBtn;
    @FXML
    private ImageView changeCategoryBtn;
    @FXML
    private ImageView backNewBtn;
    @FXML
    private ImageView helpImgView;

    @FXML
    private Pane buttonsPane;
    @FXML
    private Label popUpLabel;
    @FXML
    private Label lblCounter;
    @FXML
    private ImageView themeImgView;
    @FXML
    private Pane pane;
    @FXML
    private Label wordLabel;
    @FXML
    private ImageView hangmanImageView;

    private Game1Player game;
    private List<Image> hangmanImages;    //images of Hangman for different count of mistakes
    private List<Image> categoryImages;  //Images of categories
    public static final int HANGMAN_COUNTER = 10;
    private static final int CATEGORY_AMOUNT = 6;
    private static final int BUTTONS_ANIM = 3;

    private Map<String, ImageView> imageViewMap; //Stores imagesView, that imitate buttons. Access is provided by name of ImageView
    private Map<String, List<Image>> imageListMap;  //Stores lists of images for animations of ImageViews' click
    private List<Button> buttons;


    public void initialize() {
        initButtons();
        initImages();
        updateCounter();
        initImageViewMap();
        initImageListMap();
        putImages();

        ControllersManager.setController(this);
    }

    private void putImages() {
        ImageView imageView;
        List<String> imgViewIDs = Arrays.asList("helpImgView", "soundBtn", "mainMenuBtn", "backNewBtn", "changeCategoryBtn");
        for (int i = 0; i < 5; i++) {
            imageView = imageViewMap.get(imgViewIDs.get(i));
            imageView.setImage(imageListMap.get(imgViewIDs.get(i)).get(0));
        }
    }

    private void initImageListMap() {
//        try {
//            Connect.connect();
//            imageListMap = Connect.getImageMap();
//            Connect.closeDB();
//        } catch (SQLException e) {
////            e.printStackTrace();
//            System.out.println("ERrror in initMap!!!");
//        }
        String path = "/com/hadjower/hangman/assets/pictures/buttons/";
        List<String> imgViewIDs = Arrays.asList("helpImgView", "soundBtn", "soundDisBtn", "mainMenuBtn", "backNewBtn", "changeCategoryBtn");
        List<Image> images = new ArrayList<>(BUTTONS_ANIM);
        imageListMap = new HashMap<>();

        for (String imgViewID : imgViewIDs) {
            for (int j = 0; j < BUTTONS_ANIM; j++) {
                images.add(new Image(path + imgViewID + j + ".png"));
            }
            if (imgViewID.equals("backNewBtn")) {
                for (int j = 3; j < 6; j++) {
                    images.add(new Image(path + imgViewID + j + ".png"));
                }
            }
            imageListMap.put(imgViewID, new ArrayList<>(images));
            images.clear();
        }
    }

    private void initImageViewMap() {
        imageViewMap = new HashMap<>();
        imageViewMap.put("soundBtn", soundBtn);
        imageViewMap.put("mainMenuBtn", mainMenuBtn);
        imageViewMap.put("backNewBtn", backNewBtn);
        imageViewMap.put("helpImgView", helpImgView);
        imageViewMap.put("changeCategoryBtn", changeCategoryBtn);
    }

    private void initImages() {
        hangmanImages = new ArrayList<>(HANGMAN_COUNTER);
        String path = "/com/hadjower/hangman/assets/pictures/round/Hangman0";
        for (int i = 0; i < HANGMAN_COUNTER; i++) {
            hangmanImages.add(new Image(path + i + ".png"));
        }

        categoryImages = new ArrayList<>(CATEGORY_AMOUNT);
        path = "/com/hadjower/hangman/assets/pictures/one_player/category";
        for (int i = 0; i < CATEGORY_AMOUNT; i++) {
            categoryImages.add(new Image(path + i + ".png"));
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


    void startGame(Category category) throws IOException {
        initSceneEvent();
        if (category == Category.ALL)
            category = Category.valueOf((int) (Math.random() * CATEGORY_AMOUNT));
        themeImgView.setImage(categoryImages.get(category.getNumber()));

        game = new Game1Player(category);
        game.start();

        wordLabel.setText(game.getHiddenWord());
    }

    private void initSceneEvent() {
        pane.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                pausePanel();
            }
        });
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

    private void keyClicked(MouseEvent event) {
        KeyButton b = (KeyButton) event.getSource();
        if (b.wasClicked())
            return;
        Sound.playSound(SoundType.SCRATCH);
        String s = b.getText().toLowerCase();
        Pack pack = game.sendLetterAndGetPackage(s);

        b.getStyleClass().remove("buttonOff");
        b.getStyleClass().add(pack.isGuessed() ? "buttonGuessed" : "buttonMismatched");

        guessProcess(pack);

        b.setClicked();
    }


    public void helpReleased(MouseEvent event) {
        if (game.isHelpUsed() || !isMouseInto(event, helpImgView) || popUpPane.isVisible())
            return;

        Pack pack = game.useHelp();

        changeButtonStyle(pack);
        guessProcess(pack);

        Sound.playSound(SoundType.SCRATCH);
        helpImgView.setImage(imageListMap.get("helpImgView").get(1));
    }

    private void changeButtonStyle(Pack pack) {
        String s = "";
        for (int i = 0; i < pack.getHiddenWord().length(); i++) {
            if(wordLabel.getText().charAt(i) != pack.getHiddenWord().charAt(i)) {
                s = String.valueOf(pack.getHiddenWord().charAt(i));
                break;
            }
        }

        for (Button button : buttons) {
            if (button.getText().equals(s.toUpperCase())) {
                button.getStyleClass().remove("buttonOff");
                button.getStyleClass().add("buttonGuessed");
            }
        }
    }

    private void guessProcess(Pack pack) {
        wordLabel.setText(pack.getHiddenWord());
        if (pack.isFinished()) {
            finishGame(pack.isWin());
        } else {
            if (!pack.isGuessed()) {
                hangmanImageView.setImage(hangmanImages.get(game.getHangmanCounter()));
            }
        }
    }

    private void finishGame(boolean isWin) {
        //todo возможно следует вынести popUpPane в отдельный fxml file
        popUpLabel.setText(isWin ? "победа!" : "неудача");
        hangmanImageView.setImage(new Image("/com/hadjower/hangman/assets/pictures/round/" + (isWin ? "faceWon.png" : "faceLost.png")));
        Sound.playSound(isWin ? SoundType.WIN : SoundType.LOSE);
        buttonsPane.setVisible(false);
        popUpPane.setVisible(true);
        backNewBtn.setImage(imageListMap.get("backNewBtn").get(3));
        Game1Player.increaseCounter(isWin);
        wordLabel.setText(game.getWord());
    }

    private void updateCounter() {
        lblCounter.setText(Game1Player.getWins() + "/" + Game1Player.getDefeats());
    }

    //---Tool methods---

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

    public void helpPressed(Event event) {
        if (game.isHelpUsed() || popUpPane.isVisible())
            return;
        helpImgView.setImage(imageListMap.get("helpImgView").get(2));
    }

    public void helpMouseExited(Event event) {
        if (game.isHelpUsed())
            return;
        helpImgView.setImage(imageListMap.get("helpImgView").get(0));
    }


    public void imgViewExit(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        if (imageView.equals(backNewBtn) && (game.isHanged() || game.isWordGuessed())) {
            imageView.setImage(imageListMap.get(imageView.getId()).get(3));
        } else if (imageView.equals(soundBtn)) {
            imageView.setImage(imageListMap.get(Sound.isOn() ? imageView.getId() : "soundDisBtn").get(0));
        } else {
            imageView.setImage(imageListMap.get(imageView.getId()).get(0));
        }
    }

    public void imgViewPressed(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        if (imageView.equals(backNewBtn) && (game.isHanged() || game.isWordGuessed())) {
            imageView.setImage(imageListMap.get(imageView.getId()).get(4));
        } else if (imageView.equals(soundBtn)) {
            imageView.setImage(imageListMap.get(Sound.isOn() ? imageView.getId() : "soundDisBtn").get(1));
        } else {
            imageView.setImage(imageListMap.get(imageView.getId()).get(1));
        }
    }

    public void imgViewReleased(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        if (isMouseInto((MouseEvent) event, imageView)) {
            if (imageView.equals(backNewBtn)) {
                if (game.isHanged() || game.isWordGuessed()) {
                    imageView.setImage(imageListMap.get(imageView.getId()).get(5));
                    ControllersManager.beginOnePlayerGame(game.getCategory());
                } else {
                    pausePanel();
                }
            } else if (imageView.equals(soundBtn)) {
                imageView.setImage(imageListMap.get(Sound.isOn() ? imageView.getId() : "soundDisBtn").get(2));
                Sound.turnSound();
            } else {
                Scene scene; //NOT JavaFX
                if (imageView.equals(changeCategoryBtn)) {
                    scene = CHOOSE_CATEGORY;
                } else {
                    scene = MAIN_MENU;
                }
                HangmanStageHolder.getInstance().setScene(scene);
                imageView.setImage(imageListMap.get(imageView.getId()).get(2));

            }
            Sound.playSound(SoundType.SCRATCH);
        }
    }
}
