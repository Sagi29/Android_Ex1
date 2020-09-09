package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start_game_page extends AppCompatActivity {

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
                startActivity(intent);
            }
        });
    }

    private void findViewsByID() {
        start_game_BTN_start = findViewById(R.id.start_game_BTN_start);
        start_game_BTN_record = findViewById(R.id.start_game_BTN_record);
    }
}