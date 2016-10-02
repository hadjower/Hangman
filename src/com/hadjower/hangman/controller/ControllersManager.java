package com.hadjower.hangman.controller;

import com.hadjower.hangman.model.TwoPlayersGameManager;
import com.hadjower.hangman.view.classes.Category;
import com.hadjower.hangman.view.classes.HangmanStageHolder;
import com.hadjower.hangman.view.classes.Scene;

import java.io.IOException;

/**
 * Created by Administrator on 03.06.2016.
 */
public class ControllersManager {
    private static ChooseCategoryController chooseCategoryController;
    private static GameOneController gameSceneController;
    private static MainMenuController mainMenuController;
    private static SetNamesController setNamesController;
    private static SetWordController setWordController;
    private static GameTwoController gameTwoSceneController;

    public static void setController(Object controller) {
        if (controller instanceof ChooseCategoryController) {
                chooseCategoryController = (ChooseCategoryController) controller;
        }
        if (controller instanceof GameOneController) {
                gameSceneController = (GameOneController) controller;
        }
        if (controller instanceof MainMenuController) {
                mainMenuController = (MainMenuController) controller;
        }
        if (controller instanceof SetNamesController) {
            setNamesController = (SetNamesController) controller;
        }
        if (controller instanceof SetWordController) {
            setWordController = (SetWordController) controller;
        }
        if (controller instanceof GameTwoController) {
            gameTwoSceneController = (GameTwoController) controller;
        }
    }

    public static void beginOnePlayerGame(Category category) {
        try {
            HangmanStageHolder.getInstance().setScene(Scene.GAME_SCENE);
            gameSceneController.startGame(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void beginTwoPlayerGame(String name1, String name2) {
        TwoPlayersGameManager.getInstance().setNames(name1, name2);
        HangmanStageHolder.getInstance().setScene(Scene.SET_WORD);
    }

    public static void continueTwoPlayerGame(String s) {
        TwoPlayersGameManager.getInstance().startGame(s.toLowerCase());
        HangmanStageHolder.getInstance().setScene(Scene.GAME2_SCENE);
        gameTwoSceneController.startGame();

    }
}
