package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_result extends AppCompatActivity {

    public static final String WINNER_NAME = "WINNER_NAME";
    public static final String STEPS_NUMBER = "STEPS_NUMBER";

    private Button result_BTN_top10;
    private Button result_BTN_backToMenu;
    private TextView result_TXT_resultGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findViewsByID();
        String winner = getIntent().getStringExtra(WINNER_NAME);
        int steps = getIntent().getIntExtra(STEPS_NUMBER,0);

        result_TXT_resultGame.setText("The Winner is: " + winner + " In " + steps + "Steps");

        result_BTN_backToMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        result_BTN_top10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_result.this, activity_record.class);
                startActivity(intent);
            }
        });

    }

    private void findViewsByID() {
        result_BTN_top10.findViewById(R.id.result_BTN_top10);
        result_BTN_backToMenu.findViewById(R.id.result_BTN_backToMenu);
        result_TXT_resultGame.findViewById(R.id.result_TXT_resultGame);
    }
}