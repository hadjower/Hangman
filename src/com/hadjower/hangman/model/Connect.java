package com.hadjower.hangman.model;

import com.hadjower.hangman.view.classes.Category;
import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 08.06.2016.
 */
public class Connect {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void connect() {
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/com/hadjower/hangman/assets/words/sqlite_file.db");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("База Не Подключена!");
        } catch (ClassNotFoundException e) {
            System.out.println("Не найден класс!!!");
        }
    }

    public static void closeDB() {
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getRandomWord(Category category) {
        String string = "";
        int category_id = category.getNumber() + 1;
        try {
            resultSet = statement.executeQuery("SELECT  * FROM phrases WHERE _id = " + getIndex(category_id));
            string = resultSet.getString("phrase");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return string;
    }

    private static int getIndex(int i) throws SQLException {
        resultSet = statement.executeQuery("SELECT  * FROM phrases WHERE category_id = " + i);
        int begin = resultSet.getInt("_id");
        resultSet = statement.executeQuery("SELECT  * FROM phrases WHERE category_id = " + (i + 1));
        int end = resultSet.getInt("_id");
        return (int) (Math.random()*(end - begin) + begin);
    }

    public static HashMap<String, List<Image>> getImageMap() throws SQLException {
        HashMap<String, List<Image>> imgs = new HashMap<>();
        List<Image> img = new ArrayList<>();
        List<String> imgViewIDs = Arrays.asList("next", "soundOn", "soundOff", "rand", "mainMenuBtn", "backNewBtn", "newBtn");

        resultSet = statement.executeQuery("SELECT * FROM pathes WHERE frame NOT NULL");
        resultSet.next();
        for (String imgViewID : imgViewIDs) {
            int n = imgViewID.equals("next") ? 3 : 2;
            for (int j = 0; j < n; j++) {
                img.add(new Image(resultSet.getString("path")));
                resultSet.next();
            }
            imgs.put(imgViewID, new ArrayList<>(img));
            img.clear();
        }
        return imgs;
    }

    public static HashMap<String, Image> getRedBlue() throws SQLException {
        HashMap<String, Image> images = new HashMap<>();
        resultSet = statement.executeQuery("SELECT * FROM pathes WHERE frame ISNULL");
        while (resultSet.next()) {
            images.put(resultSet.getString("name"), new Image(resultSet.getString("path")));
        }
        return images;
    }

    public static String getRandomWord() {
        String string = "";
        try {
            resultSet = statement.executeQuery("SELECT * FROM phrases ORDER BY RANDOM() LIMIT 1");
            string = resultSet.getString("phrase");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return string;
    }
}
