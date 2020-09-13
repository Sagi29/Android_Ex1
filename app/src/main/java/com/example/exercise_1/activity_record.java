package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

public class activity_record extends AppCompatActivity {

    private Button record_BTN_menu;
    private EditText record_EDT_recordList ;
    private MySP mySP;
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
            String p1_string = mySP.getString("player_1","");
            Player p1 =gson.fromJson(p1_string, Player.class);
            ArrayList<TopTen> ttArray1 = p1.getScores();
            record_EDT_recordList.append("p1= " + p1.getName() +" sorce 1 = " + p1.getScores().size());
        }
        else{
            String p2_string = mySP.getString("player_2","");
            Player p2 =gson.fromJson(p2_string, Player.class);
            ArrayList<TopTen> ttArray2 = p2.getScores();
            record_EDT_recordList.append("p2 name=" + p2.getName() + " score 2 = "+ p2.getScores().size());
        }


        /*ArrayList<TopTen> all = new ArrayList<TopTen>();
        all.addAll(ttArray1);
        all.addAll(ttArray2);*/



       /* Log.d("ptt","p1= " + p1.getName()+ " p2= "+ p2.getName());
        Log.d("ptt","sorce 1 = " + p1.getScores().size()+ " p2 = "+ p2.getScores().size());

        record_EDT_recordList.append("p1= " + p1.getName()+ " p2= "+ p2.getName());
        record_EDT_recordList.append("sorce 1 = " + p1.getScores().size()+ " p2 = "+ p2.getScores().size());

        if(all.size() > 0)
            for (TopTen tt: all) {
                record_EDT_recordList.setText("" + tt.getNumOfMoves()+ " , ");
            }
        else
            record_EDT_recordList.append("no DATA == " + all.size());
*/
    }
}