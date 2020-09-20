package com.example.exercise_1;

import android.media.MediaPlayer;

public class Sound_App {

    private MediaPlayer pig_sound;
    private MediaPlayer bird_sound;
    private MediaPlayer win_sound;

    public Sound_App() { }

    public Sound_App(MediaPlayer pig_sound, MediaPlayer bird_sound, MediaPlayer win_sound) {
        this.pig_sound = pig_sound;
        this.bird_sound = bird_sound;
        this.win_sound = win_sound;
    }


    public MediaPlayer getPig_sound() {
        return pig_sound;
    }

    public Sound_App setPig_sound(MediaPlayer pig_sound) {
        this.pig_sound = pig_sound;
        return this;
    }

    public MediaPlayer getBird_sound() {
        return bird_sound;
    }

    public Sound_App setBird_sound(MediaPlayer bird_sound) {
        this.bird_sound = bird_sound;
        return this;
    }

    public MediaPlayer getWin_sound() {
        return win_sound;
    }

    public Sound_App setWin_sound(MediaPlayer win_sound) {
        this.win_sound = win_sound;
        return this;
    }
}
