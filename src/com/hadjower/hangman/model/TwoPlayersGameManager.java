package com.hadjower.hangman.model;

/**
 * Created by Administrator on 10.06.2016.
 */
public class TwoPlayersGameManager {
    private static TwoPlayersGameManager instance;

    private Empty player1Game;
    private Empty player2Game;
    private Empty currentGame;
    private boolean isPlayer1Turn = true;
    private int p1Wins;
    private int p2Wins;

    private TwoPlayersGameManager() {
    }

    public static TwoPlayersGameManager getInstance() {
        if (instance == null) {
            instance = new TwoPlayersGameManager();
        }
        return instance;
    }

    public void setNames(String name1, String name2) {
        player1Game = new Empty(name1);
        player2Game = new Empty(name2);
    }

    public void startGame(String word) {
        switchTurn();
        if (isPlayer1Turn) {
            currentGame = player2Game;
        } else {
            currentGame = player1Game;
        }
        currentGame.setWord(word);
        currentGame.start();
    }

    public void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
    }

    public Pack getPack(String s) {
        return currentGame.sendLetterAndGetPackage(s);
    }

    public String getName() {
        return isPlayer1Turn ? player1Game.getName() : player2Game.getName();
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public int getHangmanCounter() {
        return currentGame.getHangmanCounter();
    }

    public String getWord() {
        return currentGame.getWord();
    }

    public boolean isFinished() {
        return currentGame.isHanged() || currentGame.isWordGuessed();
    }

    public String getP2name() {
        return player2Game.getName();
    }
    public String getP1name() {
        return player1Game.getName();
    }

    public int getCurrGameWins() {
        return isPlayer1Turn ? p2Wins : p1Wins;
    }

    public int getOtherGameWins() {
        return !isPlayer1Turn ? p2Wins : p1Wins;
    }

    public String getHiddenWord() {
        return currentGame.getHiddenWord();
    }

    public void countPlus() {
        if (isPlayer1Turn)
            p2Wins++;
        else
            p1Wins++;
    }


}
