package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class Game_Activity extends AppCompatActivity {


    public static final String PLAYER_1 = "PLAYER_1";
    public static final String PLAYER_2 = "PLAYER_2";


    private int attackLevel = 0;
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
    private boolean isPlayer_1_Turn = true;


    private ArrayList<TopTen> scoresArrayListPlayer_1;
    private ArrayList<TopTen> scoresArrayListPlayer_2;
    private MySP mySP;
    private Player player_1;
    private Player player_2;
    private String theWinner;
    private long startTime = 0L;
    private Timer timer;
    private int randomAttack;
    Handler handler = new Handler();
    private Runnable runnable;
    private boolean isGameOver = false;
    //private fragment_roll_dice fragmentRoll;

    private int stepsCountForPlayer1 = 0;
    private int stepsCountForPlayer2 = 0;
    private Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_);

        mySP = new MySP(this);


        findViewsByID();
        timer = new Timer();
        random = new Random();

        retrieveData();

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

        //initFragment();


        runnable = new Runnable() {
            @Override
            public void run() {

                randomAttack = random.nextInt(3);
                Log.d("ptt", "After Random = " + randomAttack);
                clickButton();

                if (isGameOver)
                    handler.removeCallbacks(runnable);
                else
                    handler.postDelayed(this, 300);
            }
        };

        startTheGame();
    }

    private void retrieveData() {
        Gson gson = new Gson();

        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (loginPreferences.contains(PLAYER_1)) { //How can I ask here?
            String p1_string = mySP.getString(PLAYER_1,"");
            Player p1 =gson.fromJson(p1_string, Player.class);
        }
        else{
            player_1 = new Player("Bird");
            scoresArrayListPlayer_1 = new ArrayList<TopTen>();
        }
            if(loginPreferences.contains(PLAYER_2)){
                String p2_string = mySP.getString(PLAYER_2,"");
                Player p2 =gson.fromJson(p2_string, Player.class);
            }
            else {
                player_2 = new Player("Pig");
                scoresArrayListPlayer_2 = new ArrayList<TopTen>();
            }
    }

    private void startTheGame() {
        //removeFragmentRoll();
        handler.postDelayed(runnable, 300);
    }

    private void removeFragmentRoll() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.game_LAY_rollDice);
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    private void initFragment() {
        fragment_roll_dice roll = new fragment_roll_dice();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.game_LAY_rollDice, roll);
        transaction.commit();
        Log.d("ptt", "IN initFragment");
    }


    private void startRepeatingTask() {
        handler.post(runnable);
    }

    private void stopRepeatingTask() {
        handler.removeCallbacks(runnable);
        Log.d("ptt", "STOP HANDLER");
    }

    private void clickButton() {
        switch (randomAttack) {
            case 0:
                if (isPlayer_1_Turn)
                    game_BTN_high_attack_player1.performClick();
                else
                    game_BTN_high_attack_player2.performClick();
                break;
            case 1:
                if (isPlayer_1_Turn)
                    game_BTN_medium_attack_player1.performClick();
                else
                    game_BTN_medium_attack_player2.performClick();
                break;
            case 2:
                if (isPlayer_1_Turn)
                    game_BTN_low_attack_player1.performClick();
                else
                    game_BTN_low_attack_player2.performClick();
                break;
        }
    }

    private View.OnClickListener buttonAttackClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (((String) view.getTag()).equals(game_BTN_high_attack_player1.getTag().toString()) ||
                    ((String) view.getTag()).equals(game_BTN_high_attack_player2.getTag()))
                attackLevel = 20;

            if (((String) view.getTag()).equals(game_BTN_medium_attack_player1.getTag().toString()) ||
                    ((String) view.getTag()).equals(game_BTN_medium_attack_player2.getTag()))
                attackLevel = 10;

            if (((String) view.getTag()).equals(game_BTN_low_attack_player1.getTag().toString()) ||
                    ((String) view.getTag()).equals(game_BTN_low_attack_player2.getTag()))
                attackLevel = 5;

            manageGame();
        }
    };

    private void findViewsByID() {
        game_IMG_bird = findViewById(R.id.game_IMG_bird);
        game_IMG_pig = findViewById(R.id.game_IMG_pig);
        game_BTN_high_attack_player1 = findViewById(R.id.game_BTN_high_attack_player1);
        game_BTN_medium_attack_player1 = findViewById(R.id.game_BTN_medium_attack_player1);
        game_BTN_low_attack_player1 = findViewById(R.id.game_BTN_low_attack_player1);
        game_BTN_high_attack_player2 = findViewById(R.id.game_BTN_high_attack_player2);
        game_BTN_medium_attack_player2 = findViewById(R.id.game_BTN_medium_attack_player2);
        game_BTN_low_attack_player2 = findViewById(R.id.game_BTN_low_attack_player2);
        game_ProgressBar_player1 = findViewById(R.id.game_PRB_player1);
        game_ProgressBar_player2 = findViewById(R.id.game_PRB_player2);
    }


    private void manageGame() {

        if (isPlayer_1_Turn) {
            game_ProgressBar_player2.setProgress(game_ProgressBar_player2.getProgress() - attackLevel);
            if (game_ProgressBar_player2.getProgress() < 30)
                game_ProgressBar_player2.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            isPlayer_1_Turn = false;
            stepsCountForPlayer1++;
        } else {
            game_ProgressBar_player1.setProgress(game_ProgressBar_player1.getProgress() - attackLevel);
            if (game_ProgressBar_player1.getProgress() < 30)
                game_ProgressBar_player1.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            isPlayer_1_Turn = true;
            stepsCountForPlayer2++;
        }
        if (game_ProgressBar_player1.getProgress() == 0 || game_ProgressBar_player2.getProgress() == 0)
            //onStop();
            gameEnd();
        else
            manageButton();
    }

    private void gameEnd() {

        Intent intent = new Intent(this, Activity_result.class);
        //TODO save WINNER + STEPS data

        TopTen top10;
        Gson gson = new Gson();

        if(stepsCountForPlayer1 >= stepsCountForPlayer2) {
            theWinner = player_1.getName();
            intent.putExtra(Activity_result.STEPS_NUMBER,stepsCountForPlayer1);
            top10 = new TopTen(33.33,-13.52,System.currentTimeMillis(),stepsCountForPlayer1);
            scoresArrayListPlayer_1.add(top10);
            player_1.setScores(scoresArrayListPlayer_1);
            String json = gson.toJson(player_1);
            mySP.putString(PLAYER_1,json);
        }
        else {
            theWinner = player_2.getName();
            intent.putExtra(Activity_result.STEPS_NUMBER,stepsCountForPlayer2);
            top10 = new TopTen(33.33,-13.52,System.currentTimeMillis(),stepsCountForPlayer2);
            scoresArrayListPlayer_2.add(top10);
            player_2.setScores(scoresArrayListPlayer_2);
            String json = gson.toJson(player_2);
            mySP.putString(PLAYER_2,json);
        }
        intent.putExtra(Activity_result.WINNER_NAME,theWinner);
        isGameOver = true;

        startActivity(intent);
        finish();
        }

        private void manageButton() {

            game_BTN_high_attack_player1.setEnabled(isPlayer_1_Turn);
            game_BTN_medium_attack_player1.setEnabled(isPlayer_1_Turn);
            game_BTN_low_attack_player1.setEnabled(isPlayer_1_Turn);

            game_BTN_high_attack_player2.setEnabled(!isPlayer_1_Turn);
            game_BTN_medium_attack_player2.setEnabled(!isPlayer_1_Turn);
            game_BTN_low_attack_player2.setEnabled(!isPlayer_1_Turn);

        }

}