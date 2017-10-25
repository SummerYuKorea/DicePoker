package edu.android.and_dicepoker;

/**
 * Created by stu on 2017-03-17.
 */

public class GameTree {
    private String tree;

    public GameTree() {}

    public GameTree(String tree) {
        this.tree = tree;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return tree;
    }
}
