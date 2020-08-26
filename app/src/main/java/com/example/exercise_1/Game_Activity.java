package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Game_Activity extends AppCompatActivity {


    private  int attackLevel = 0;


    private ImageView game_IMG_bird;
    private ImageView game_IMG_pig;
    private Button game_BTN_high_attack_player1;
    private Button game_BTN_medium_attack_player1;
    private Button game_BTN_low_attack_player1;
    private Button game_BTN_high_attack_player2;
    private Button game_BTN_medium_attack_player2;
    private Button game_BTN_low_attack_player2;


     private ProgressBar game_ProgressBar_player1;
     private ProgressBar game_ProgressBar_player2;
    private boolean isPlayer_1_Turn = false;
    //player 1 = -1 , player 2 = 1
    //private int playerTurn = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_);

        findViewsByID();
        manageButton();

        Glide
               .with(this)
               .load(R.drawable.bird)
               .centerCrop()
               .into(game_IMG_bird);

        Glide
                .with(this)
                .load(R.drawable.pig)
                .centerCrop()
                .into(game_IMG_pig);

        game_BTN_high_attack_player1.setOnClickListener(buttonAttackClick);
        game_BTN_medium_attack_player1.setOnClickListener(buttonAttackClick);
        game_BTN_low_attack_player1.setOnClickListener(buttonAttackClick);
        game_BTN_high_attack_player2.setOnClickListener(buttonAttackClick);
        game_BTN_medium_attack_player2.setOnClickListener(buttonAttackClick);
        game_BTN_low_attack_player2.setOnClickListener(buttonAttackClick);




    }

    private View.OnClickListener buttonAttackClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(((String)view.getTag()).equals(game_BTN_high_attack_player1.getTag().toString()) ||
                    ((String)view.getTag()).equals(game_BTN_high_attack_player2.getTag()))
                        attackLevel = 20;

            if(((String)view.getTag()).equals(game_BTN_medium_attack_player1.getTag().toString()) ||
                    ((String)view.getTag()).equals(game_BTN_medium_attack_player2.getTag()))
                attackLevel = 10;

            if(((String)view.getTag()).equals(game_BTN_low_attack_player1.getTag().toString()) ||
                    ((String)view.getTag()).equals(game_BTN_low_attack_player2.getTag()))
                attackLevel = 5;

            manageGame();
        }
    };

    private void findViewsByID(){
        game_IMG_bird = findViewById(R.id.game_IMG_bird);
        game_IMG_pig = findViewById(R.id.game_IMG_pig);
        game_BTN_high_attack_player1 = findViewById(R.id.game_BTN_high_attack_player1);
        game_BTN_medium_attack_player1= findViewById(R.id.game_BTN_medium_attack_player1);
        game_BTN_low_attack_player1 = findViewById(R.id.game_BTN_low_attack_player1);
        game_BTN_high_attack_player2 = findViewById(R.id.game_BTN_high_attack_player2);
        game_BTN_medium_attack_player2 = findViewById(R.id.game_BTN_medium_attack_player2);
        game_BTN_low_attack_player2 = findViewById(R.id.game_BTN_low_attack_player2);
        game_ProgressBar_player1 = findViewById(R.id.game_PRB_player1);
        game_ProgressBar_player2 = findViewById(R.id.game_PRB_player2);
    }


    private void manageGame(){


        if(isPlayer_1_Turn) {
            game_ProgressBar_player2.setProgress(game_ProgressBar_player2.getProgress() - attackLevel);
            if(game_ProgressBar_player2.getProgress() < 30)
                game_ProgressBar_player2.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            isPlayer_1_Turn = false;
        }
        else {
           game_ProgressBar_player1.setProgress(game_ProgressBar_player1.getProgress() - attackLevel);
            if(game_ProgressBar_player1.getProgress() < 30)
                game_ProgressBar_player1.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            isPlayer_1_Turn = true;
        }
        if(game_ProgressBar_player1.getProgress() == 0 || game_ProgressBar_player2.getProgress() == 0)
            onStop();
        else
            manageButton();
    }

    private void manageButton(){

        game_BTN_high_attack_player1.setEnabled(isPlayer_1_Turn);
        game_BTN_medium_attack_player1.setEnabled(isPlayer_1_Turn);
        game_BTN_low_attack_player1.setEnabled(isPlayer_1_Turn);

        game_BTN_high_attack_player2.setEnabled(!isPlayer_1_Turn);
        game_BTN_medium_attack_player2.setEnabled(!isPlayer_1_Turn);
        game_BTN_low_attack_player2.setEnabled(!isPlayer_1_Turn);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
        game_BTN_high_attack_player1.setEnabled(false);
        game_BTN_medium_attack_player1.setEnabled(false);
        game_BTN_low_attack_player1.setEnabled(false);

        game_BTN_high_attack_player2.setEnabled(false);
        game_BTN_medium_attack_player2.setEnabled(false);
        game_BTN_low_attack_player2.setEnabled(false);

    }

}