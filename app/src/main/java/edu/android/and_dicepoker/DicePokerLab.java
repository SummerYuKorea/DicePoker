package edu.android.and_dicepoker;

import java.util.ArrayList;

/**
 * Created by stu on 2017-03-09.
 */

public class DicePokerLab {
    private static final int[] PHOTO_IDS = {
            R.drawable.heard,R.drawable.i1,
            R.drawable.cup,R.drawable.cup,
            R.drawable.crystal,R.drawable.crystal,
            R.drawable.time,R.drawable.star,
            R.drawable.star,R.drawable.flash
            //R.drawable.dice6,

    };

    private ArrayList<DicePoker> dicePokerList;
    private static DicePokerLab instance;

    private DicePokerLab() {
        dicePokerList = new ArrayList<>();
        makeDummyList();

    }

    public static DicePokerLab getInstance() {
        if (instance == null) {
            instance = new DicePokerLab();
        }
        return instance;
    }

    public ArrayList<DicePoker> getDicePokerList() {
        return dicePokerList;
    }

    private void makeDummyList() {
        for (int i = 0; i < 10; i++) {
            DicePoker d = new DicePoker();
            if(i == 0) {
                d.setAaa("도전과제: 1 \n" +
                        " 보이스 \n 최초 획득" );
                d.setBbb("보상: 100 스타" );
            } else if (i == 1) {
                d.setAaa("도전과제: 2 \n 스킨 \n 최초 획득" );
                d.setBbb("보상: 100 스타" );
            } else if (i == 2) {
                d.setAaa("도전과제: 3 \n" +
                        " 주사위 묶지 않고 \n" +
                        " 승리 10승" );
                d.setBbb("보상: 200 스타" );
            } else if (i == 3) {
                d.setAaa("도전과제: 4 \n" +
                        " 주사위 묶지 않고 \n" +
                        " 승리 100승" );
                d.setBbb("보상: 스킨8 획득" );
            } else if (i == 4) {
                d.setAaa("도전과제: 5 \n" +
                        " 연승 \n" +
                        " 5승 이상" );
                d.setBbb("보상: 500 골드" );
            } else if (i == 5) {
                d.setAaa("도전과제: 6 \n" +
                        " 연승 \n" +
                        " 10승 이상" );
                d.setBbb("보상: 1000 골드" );
            } else if (i == 6) {
                d.setAaa("도전과제: 7 \n" +
                        " 총 승리 \n" +
                        " 1000승 이상" );
                d.setBbb("보상: Voice 한결 획득" );
            } else if (i == 7) {
                d.setAaa("도전과제: 8 \n" +
                        " 보유스타 \n 10000이상" );
                d.setBbb("보상: 스킨7 획득" );
            } else if (i == 8) {
                d.setAaa("도전과제: 9 \n" +
                        " 보유스타 \n 20000이상" );
                d.setBbb("보상: 스킨8 획득" );
            } else if (i == 9) {
                d.setAaa("도전과제: 10 \n" +
                        " 칭호 \n Destroyer 이상 획득" );
                d.setBbb("보상: Voice 용선 획득" );
            }
//            d.setAaa("도전과제: " + i);
//            d.setBbb("보상: " + i);
            d.setPhotoId(PHOTO_IDS[i % PHOTO_IDS.length]);
            dicePokerList.add(d);

        }
    }
}

