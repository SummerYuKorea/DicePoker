package edu.android.and_dicepoker;

import java.util.ArrayList;

/**
 * Created by stu on 2017-03-13.
 */

public class StoreLab {
    private static final int[] PHOTO_IDS = {
            //사운드 1,2,3,4,5
            R.drawable.f1, R.drawable.f2,
            R.drawable.f3, R.drawable.f4,
            R.drawable.f5,
            //기본스킨 0(0원) ,1,2,3
            R.drawable.background0,R.drawable.background1,
            R.drawable.background2, R.drawable.background3,
            //추가스킨 4,5,6,7
            R.drawable.backskinitem4,R.drawable.backskinitem5,
            R.drawable.backskinitem6, R.drawable.backskinitem7,
            //다이스스킨 1,2,3,4
            R.drawable.background4, R.drawable.background5,
            R.drawable.item10, R.drawable.item11

    };

    private ArrayList<Store> storeList;
    private static StoreLab instance;

    private StoreLab() {
        storeList = new ArrayList<>();
        makeDummyList();

    }

    public static StoreLab getInstance() {
        if (instance == null) {
            instance = new StoreLab();
        }
        return instance;
    }

    public ArrayList<Store> getStoreList() {
        return storeList;
    }


    private void makeDummyList() {
        for (int i = 0; i < 17; i++) {
            Store d = new Store();
            if(i == 0) {
                d.setAaa("Sound 1" );
                d.setBbb("1000" );
            } else if (i == 1) {
                d.setAaa("Sound 2" );
                d.setBbb("1100" );
            } else if (i == 2) {
                d.setAaa("Sound 3" );
                d.setBbb("1200" );
            } else if (i == 3) {
                d.setAaa("Sound 4" );
                d.setBbb("1300" );
            } else if (i == 4) {
                d.setAaa("Sound 5" );
                d.setBbb("1400" );
            } else if (i == 5) {
                d.setAaa("배경 스킨 ");
                d.setBbb("0");
            } else if (i == 6) {
                    d.setAaa("배경 스킨 " );
                    d.setBbb("2000" );
            } else if (i == 7) {
                d.setAaa("배경 스킨 ");
                d.setBbb("3000" );
            } else if (i == 8) {
                d.setAaa("배경 스킨 " );
                d.setBbb("4000" );
            } else if (i == 9) {
                d.setAaa("배경 스킨 ");
                d.setBbb("5000");
            } else if (i == 10) {
                d.setAaa("배경 스킨 " );
                d.setBbb("6000" );
            } else if (i == 11) {
                d.setAaa("배경 스킨 ");
                d.setBbb("7000" );
            } else if (i == 12) {
                d.setAaa("배경 스킨 " );
                d.setBbb("8000" );
            } else if (i == 13) {
                d.setAaa("Dice Skin " );
                d.setBbb("5000" );
            } else if (i == 14) {
                d.setAaa("Dice Skin " );
                d.setBbb("6000" );
            } else if (i == 15) {
                d.setAaa("Dice Skin " );
                d.setBbb("7000" );
            } else if (i == 16) {
                d.setAaa("Dice Skin " );
                d.setBbb("8000" );
            }
//            d.setAaa("도전과제: " + i);
//            d.setBbb("보상: " + i);
            d.setPhotoId(PHOTO_IDS[i % PHOTO_IDS.length]);
            storeList.add(d);

        }
    }
}