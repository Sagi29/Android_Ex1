package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class activity_record extends AppCompatActivity {

    private String previousActivity;
    private Button record_BTN_menu;
    private EditText record_EDT_recordList ;
    private MySP mySP;
    private Player p1;
    private Player p2;
    ArrayList<TopTen> ttArray1;
    ArrayList<TopTen> ttArray2;
    ArrayList<TopTen> bestTTlist;
    Fragment googleMapFragment;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mySP = new MySP(this);
        Gson gson = new Gson();
        Intent intent = getIntent();
        previousActivity = intent.getStringExtra(Start_game_page.PREVIOUS_ACTIVITY_NAME);
        record_EDT_recordList = findViewById(R.id.record_EDT_recordList);
        record_BTN_menu = findViewById(R.id.record_BTN_menu);
        ttArray1 = new ArrayList<>();
        ttArray2 = new ArrayList<>();
        if(previousActivity != null){ //came from Start Game Page Activity
            retrieveData();
        }else{       //came from Game Activity
            String winner = intent.getStringExtra(Activity_result.WINNER_NAME);
            if(winner.equals("Bird")){
                String p1_string = mySP.getString(Game_Activity.PLAYER_1_NAME,"");
                p1 =gson.fromJson(p1_string, Player.class);
                if(! ttArray1.isEmpty())
                    ttArray1.removeAll(ttArray1);
                ttArray1 = p1.getScores();
            }
            else{
                String p2_string = mySP.getString(Game_Activity.PLAYER_2_NAME,"");
                p2 =gson.fromJson(p2_string, Player.class);
                if(! ttArray2.isEmpty())
                    ttArray2.removeAll(ttArray2);
                ttArray2 = p2.getScores();
            }
        }

       // googleMap = (findViewById(R.id.record_map).getRootView());
        record_BTN_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initFragment();
        getBestTop10Score();
        showBestTop10Score();
    }

    private void retrieveData() {
        Gson gson = new Gson();

        SharedPreferences loginPreferences = getSharedPreferences("MY_SP", MODE_PRIVATE);
        if (loginPreferences.contains(Game_Activity.PLAYER_1_NAME)) {
            String p1_string = mySP.getString(Game_Activity.PLAYER_1_NAME,"");
            p1 =gson.fromJson(p1_string, Player.class);
            ttArray1 = p1.getScores();
        }
        else{
            p1 = new Player("Bird");
            ttArray1 = new ArrayList<TopTen>();
        }
        if(loginPreferences.contains(Game_Activity.PLAYER_2_NAME)){
            String p2_string = mySP.getString(Game_Activity.PLAYER_2_NAME,"");
            p2 =gson.fromJson(p2_string, Player.class);
            ttArray2 = p2.getScores();
        }
        else {
            p2 = new Player("Pig");
            ttArray2 = new ArrayList<TopTen>();
        }
    }

    private void showBestTop10Score() {
        record_EDT_recordList.setText("");
        for (TopTen tt: bestTTlist) {
            record_EDT_recordList.append(tt.toString() + "\n");
        }
    }

    private void getBestTop10Score() {
        if(bestTTlist != null && ! bestTTlist.isEmpty())
            bestTTlist.removeAll(bestTTlist);
        else
            bestTTlist = new ArrayList<TopTen>();
        if(ttArray1 != null)
            bestTTlist.addAll(ttArray1);
        if(ttArray2 != null)
            bestTTlist.addAll(ttArray2);
        //int minIndex = list.indexOf(Collections.min(list));
        while (bestTTlist.size() > 10){
            TopTen tt = Collections.max(bestTTlist, new TtComp());
            bestTTlist.remove(tt);
        }
    }

    private void initFragment() {
        googleMapFragment = new fragment_map();
        //googleMap.setActivityCallBack(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.record_LAY_googleMap, googleMapFragment);
        transaction.commit();
    }
}