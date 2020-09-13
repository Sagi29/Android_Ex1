package com.example.exercise_1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Player {

    private String name;
    private ArrayList<TopTen> scores;
    public Player() { }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<TopTen> getScores() {
        return scores;
    }

    public Player setScores(ArrayList<TopTen> scores) {
        this.scores = scores;
        if(scores.size() > 10) {
            TopTen tt = Collections.min(scores, new TtComp());
            scores.remove(tt);
        }
        return this;
    }
}

class TtComp implements Comparator<TopTen> {

public int compare(TopTen tt1, TopTen tt2) {
    if(tt1.getNumOfMoves() > tt2.getNumOfMoves())
            return 1;
        else
            return 0;
}
}
