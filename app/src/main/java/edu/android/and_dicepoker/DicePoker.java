package edu.android.and_dicepoker;

/**
 * Created by stu on 2017-03-09.
 */

public class DicePoker {
    private String aaa, bbb;
    private int aa,photoId;

    public DicePoker() {}

    public DicePoker(String aaa, String bbb, int aa, int photoId) {
        this.aaa = aaa;
        this.bbb = bbb;
        this.aa = aa;
        this.photoId = photoId;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bbb) {
        this.bbb = bbb;
    }

    public int getAa() {
        return aa;
    }

    public void setAa(int aa) {
        this.aa = aa;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return aaa;
    }
}

