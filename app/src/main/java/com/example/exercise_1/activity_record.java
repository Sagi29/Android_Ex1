package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class activity_record extends AppCompatActivity {

    private Button record_BTN_menu;
    private EditText record_EDT_recordList ;
    private MySP mySP;
    private Player p1;
    private Player p2;
    ArrayList<TopTen> ttArray1;
    ArrayList<TopTen> ttArray2;
    ArrayList<TopTen> bestTTlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mySP = new MySP(this);
        Gson gson = new Gson();
        String winner = getIntent().getStringExtra(Activity_result.WINNER_NAME);
        record_EDT_recordList = findViewById(R.id.record_EDT_recordList);
        record_BTN_menu = findViewById(R.id.record_BTN_menu);

        record_BTN_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(winner.equals("Bird")){
            String p1_string = mySP.getString(Game_Activity.PLAYER_1,"");
            p1 =gson.fromJson(p1_string, Player.class);
            ttArray1 = p1.getScores();

        }
        else{
            String p2_string = mySP.getString(Game_Activity.PLAYER_2,"");
            p2 =gson.fromJson(p2_string, Player.class);
            ttArray2 = p2.getScores();

        }


        getBestTop10Score();
        showBestTop10Score();

    }

    private void showBestTop10Score() {
        for (TopTen tt: bestTTlist) {
            record_EDT_recordList.append(tt.toString() + "\n");
        }
    }

    private void getBestTop10Score() {
        bestTTlist = new ArrayList<TopTen>();
        if(ttArray1 != null)
            bestTTlist.addAll(ttArray1);
        if(ttArray2 != null)
            bestTTlist.addAll(ttArray2);
        Log.d("ptt","total in array = "+ bestTTlist.size());
        //int minIndex = list.indexOf(Collections.min(list));
        while (bestTTlist.size() > 10){
            TopTen tt = Collections.max(bestTTlist, new TtComp());
            bestTTlist.remove(tt);
        }
    }
}