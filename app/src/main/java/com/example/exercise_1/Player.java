package com.example.exercise_1;

import java.util.ArrayList;

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
        return this;
    }
}
