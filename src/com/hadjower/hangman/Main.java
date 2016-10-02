package com.hadjower.hangman;

import com.hadjower.hangman.view.classes.HangmanStageHolder;
import com.hadjower.hangman.view.classes.Scene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            HangmanStageHolder.getInstance().setStage(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Виселица");
        HangmanStageHolder.getInstance().setScene(Scene.MAIN_MENU);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
