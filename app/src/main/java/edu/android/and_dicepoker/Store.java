package edu.android.and_dicepoker;

/**
 * Created by stu on 2017-03-13.
 */

public class Store {
    private String aaa, bbb, ccc;
    private int aa,photoId;

    public Store() {}

    public Store(String aaa, String bbb, String ccc, int aa, int photoId) {
        this.aaa = aaa;
        this.bbb = bbb;
        this.ccc = ccc;
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

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
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

