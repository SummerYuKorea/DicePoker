package edu.android.and_dicepoker;

import java.util.ArrayList;

/**
 * Created by stu on 2017-03-17.
 */

public class GameTreeLab {
    private static final String[] TREE_IDS = {

            "원페어","투페어","트리플","풀하우스!",
            "포다이스!","파이브다이스!","스트레이트!","8","9"

    };


    private ArrayList<GameTree> gameTreeList;
    private static GameTreeLab instance;

    private GameTreeLab() {
        gameTreeList = new ArrayList<>();
        makeDummyList();

    }

    public static GameTreeLab getInstance() {
        if (instance == null) {
            instance = new GameTreeLab();
        }
        return instance;
    }

    public ArrayList<GameTree> getGameTreeList() {
        return gameTreeList;
    }

    private void makeDummyList() {

        for (int i = 0; i < 9; i++) {

            GameTree d = new GameTree();


            d.setTree((i+1) + " : " + TREE_IDS[i]);

                gameTreeList.add(d);

            }
        }
    }

