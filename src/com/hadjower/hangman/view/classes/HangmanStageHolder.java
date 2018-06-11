package com.hadjower.hangman.view.classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import static com.hadjower.hangman.view.classes.Scene.*;

/**
 * Created by Administrator on 03.06.2016.
 */
public class HangmanStageHolder {
    private Stage stage;
    private static HangmanStageHolder instance;
    private static HashMap<Scene, URL> roots;

    private HangmanStageHolder() {
        roots = new HashMap<>();
        roots.put(MAIN_MENU, getClass().getResource("/com/hadjower/hangman/view/fxml/main_menu.fxml"));
        roots.put(CHOOSE_CATEGORY, getClass().getResource("/com/hadjower/hangman/view/fxml/choose_category.fxml"));
        roots.put(GAME_SCENE, getClass().getResource("/com/hadjower/hangman/view/fxml/game_scene.fxml"));
        roots.put(SET_NAME, getClass().getResource("/com/hadjower/hangman/view/fxml/set_names.fxml"));
        roots.put(SET_WORD, getClass().getResource("/com/hadjower/hangman/view/fxml/set_word.fxml"));
        roots.put(GAME2_SCENE, getClass().getResource("/com/hadjower/hangman/view/fxml/game2_scene.fxml"));
    }

    public void setStage(Stage stage) throws IOException {
        if (this.stage != null) {
            throw new IOException("Stage already set!");
        }
        this.stage = stage;
    }


    public static HangmanStageHolder getInstance() {
        if (instance == null) {
            instance = new HangmanStageHolder();
        }
        return instance;
    }

    public void setScene(Scene scene) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(roots.get(scene));
            stage.setScene(new javafx.scene.Scene(root, 1200, 750));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
