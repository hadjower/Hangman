package com.hadjower.hangman.model;

import com.hadjower.hangman.view.classes.Category;
import javafx.scene.image.Image;
import oracle.jdbc.OracleTypes;

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
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:src/com/hadjower/hangman/assets/words/sqlite_file.db");
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
                    "nastya", "1111");
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
        try (CallableStatement cs = connection.prepareCall("{?=call GET_RAND_WORD_BY_CAT(?)}")) {
            cs.registerOutParameter(1, OracleTypes.VARCHAR);
            cs.setInt(2, category_id);
            cs.execute();
            string = cs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return string;
    }

//    private static int getIndex(int i) throws SQLException {
//        resultSet = statement.executeQuery("SELECT  * FROM phrases WHERE category_id = " + i);
//        resultSet.next();
//        int begin = resultSet.getInt("id");
//        resultSet = statement.executeQuery("SELECT  * FROM phrases WHERE category_id = " + (i + 1));
//        resultSet.next();
//        int end = resultSet.getInt("id");
//        return (int) (Math.random()*(end - begin) + begin);
//    }

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
        try (CallableStatement cs = connection.prepareCall("{?=call GET_RAND_WORD()}")) {
            cs.registerOutParameter(1, OracleTypes.VARCHAR);
            cs.execute();
            string = cs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return string;
    }
}
