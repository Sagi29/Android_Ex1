package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Start_game_page extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "start game page";
    public static final String PREVIOUS_ACTIVITY_NAME = "previous activity";
    private Button start_game_BTN_start;
    private Button start_game_BTN_record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_page);

        findViewsByID();

        
        start_game_BTN_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start_game_page.this, Game_Activity.class);
                startActivity(intent);
            }
        });


        start_game_BTN_record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start_game_page.this, activity_record.class);
                intent.putExtra(PREVIOUS_ACTIVITY_NAME,ACTIVITY_NAME);
                startActivity(intent);
            }
        });
    }

    /*private void retrieveData() {

        Gson gson = new Gson();

        SharedPreferences loginPreferences = getSharedPreferences("MY_SP", MODE_PRIVATE);
        if (loginPreferences.contains(PLAYER_1_name)) { //How can I ask here?
            String p1_string = mySP.getString(PLAYER_1_name,"");
            player_1 =gson.fromJson(p1_string, Player.class);
            scoresArrayListPlayer_1 = player_1.getScores();
            Log.d("ptt","In retrieveData PLAYER1");
        }
        else{
            player_1 = new Player("Bird");
            scoresArrayListPlayer_1 = new ArrayList<TopTen>();
            Log.d("ptt","In retrieveData NEW !! PLAYER1");
        }
        if(loginPreferences.contains(PLAYER_2_name)){
            String p2_string = mySP.getString(PLAYER_2_name,"");
            player_2 =gson.fromJson(p2_string, Player.class);
            scoresArrayListPlayer_2 = player_2.getScores();
            Log.d("ptt","In retrieveData PLAYER2");
        }
        else {
            player_2 = new Player("Pig");
            scoresArrayListPlayer_2 = new ArrayList<TopTen>();
            Log.d("ptt","In retrieveData NEW !! PLAYER2");
        }
    }*/

    private void findViewsByID() {
        start_game_BTN_start = findViewById(R.id.start_game_BTN_start);
        start_game_BTN_record = findViewById(R.id.start_game_BTN_record);
    }
}