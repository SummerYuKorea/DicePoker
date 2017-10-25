package edu.android.and_dicepoker;

import java.io.Serializable;

/**
 * Created by stu on 2017-03-14.
 */

public class UserVO implements Serializable{

    //멤버변수 (필드)
    private String userId;
    private String passWd;
    private int win;
    private int lose;
    private int gold; //용선오빠가 db에 string으로 설정
    private String title; //title=="destoyer" //id=R.drawable.c1
    private int totalGame;
    private int gameOn; //true: 게임중///게임중 대신 profileImage로 사용해야함!!

    private int diceSkin;
    private int backSkin;
    private int voice;

    public UserVO() {
    }

    public UserVO(String userId, String passWd){
        this.userId = userId;
        this.passWd = passWd;
    }

    public UserVO(String userId, String passWd, int win, int lose,
                  int gold, String title, int totalGame, int gameOn, int diceSkin, int backSkin, int voice) {
        this.userId = userId;
        this.passWd = passWd;
        this.win = win;
        this.lose = lose;
        this.gold = gold;
        this.title = title;
        this.totalGame = totalGame;
        this.gameOn = gameOn;
        this.diceSkin = diceSkin;
        this.backSkin = backSkin;
        this.voice = voice;// 0:희석 1:용욱 2:호영 3:용선 4:한결
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(int totalGame) {
        this.totalGame = totalGame;
    }

    public int getGameOn() {
        return gameOn;
    }

    public void setGameOn(int gameOn) {
        this.gameOn = gameOn;
    }

    public int getDiceSkin() {
        return diceSkin;
    }

    public void setDiceSkin(int diceSkin) {
        this.diceSkin = diceSkin;
    }

    public int getBackSkin() {
        return backSkin;
    }

    public void setBackSkin(int backSkin) {
        this.backSkin = backSkin;
    }

    public int getVoice() {
        return voice;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }
}
