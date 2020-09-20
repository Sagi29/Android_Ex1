package com.example.exercise_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.BoringLayout;
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



public class Game_Activity extends AppCompatActivity implements CallBack_GameActivity{


    public static final String PLAYER_1_NAME = "PLAYER_1";
    public static final String PLAYER_2_NAME = "PLAYER_2";
    public static final String ACTIVITY_NAME = "GAME";

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


    private fragment_roll_dice roll;
    private Boolean start = false;
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

    private int stepsCountForPlayer1 = 0;
    private int stepsCountForPlayer2 = 0;
    private Random random;

   private Sound_App sound_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_);

        mySP = new MySP(this);

        sound_app = new Sound_App();
        sound_app.setBird_sound(MediaPlayer.create(Game_Activity.this,R.raw.birds));
        sound_app.setPig_sound(MediaPlayer.create(Game_Activity.this,R.raw.pig));
        sound_app.setWin_sound(MediaPlayer.create(Game_Activity.this,R.raw.win));



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

        initFragment();

        runnable = new Runnable() {
            @Override
            public void run() {
                
                randomAttack = random.nextInt(3);
                clickButton();

                if (isGameOver)
                    handler.removeCallbacks(runnable);
                else
                    handler.postDelayed(this, 2000);
            }
        };
    }

    private void retrieveData() {
        Gson gson = new Gson();

        SharedPreferences loginPreferences = getSharedPreferences("MY_SP", MODE_PRIVATE);
        if (loginPreferences.contains(PLAYER_1_NAME)) {
            String p1_string = mySP.getString(PLAYER_1_NAME,"");
            player_1 =gson.fromJson(p1_string, Player.class);
            scoresArrayListPlayer_1 = player_1.getScores();
        }
        else{
            player_1 = new Player("Bird");
            scoresArrayListPlayer_1 = new ArrayList<TopTen>();
        }
            if(loginPreferences.contains(PLAYER_2_NAME)){
                String p2_string = mySP.getString(PLAYER_2_NAME,"");
                player_2 =gson.fromJson(p2_string, Player.class);
                scoresArrayListPlayer_2 = player_2.getScores();
            }
            else {
                player_2 = new Player("Pig");
                scoresArrayListPlayer_2 = new ArrayList<TopTen>();
            }
    }

    private void startTheGame() {
        handler.postDelayed(runnable, 2000);
    }


    private void initFragment() {
        roll = new fragment_roll_dice();
        roll.setActivityCallBack(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.game_LAY_rollDice, roll);
        transaction.commit();
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
            sound_app.getBird_sound().start();
            game_ProgressBar_player2.setProgress(game_ProgressBar_player2.getProgress() - attackLevel);
            if (game_ProgressBar_player2.getProgress() < 30)
                game_ProgressBar_player2.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            isPlayer_1_Turn = false;
            stepsCountForPlayer1++;
        } else {
            sound_app.getPig_sound().start();

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

        sound_app.getWin_sound().start();

        // save WINNER + STEPS data
        TopTen top10;
        Gson gson = new Gson();

        if(stepsCountForPlayer1 >= stepsCountForPlayer2) {
            theWinner = player_1.getName();
            intent.putExtra(Activity_result.STEPS_NUMBER,stepsCountForPlayer1);
            top10 = new TopTen(33.33,-13.52,System.currentTimeMillis(),stepsCountForPlayer1);
            scoresArrayListPlayer_1.add(top10);
            player_1.setScores(scoresArrayListPlayer_1);
            String json = gson.toJson(player_1);
            mySP.putString(PLAYER_1_NAME,json);
        }
        else {
            theWinner = player_2.getName();
            intent.putExtra(Activity_result.STEPS_NUMBER,stepsCountForPlayer2);
            top10 = new TopTen(33.33,-13.52,System.currentTimeMillis(),stepsCountForPlayer2);
            scoresArrayListPlayer_2.add(top10);
            player_2.setScores(scoresArrayListPlayer_2);
            String json = gson.toJson(player_2);
            mySP.putString(PLAYER_2_NAME,json);
        }
        intent.putExtra(Activity_result.WINNER_NAME,theWinner);
        intent.putExtra(Start_game_page.PREVIOUS_ACTIVITY_NAME,ACTIVITY_NAME);
        isGameOver = true;

        startActivity(intent);
        sound_app.getWin_sound().stop();
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

    @Override
    public void rollResult(int r1, int r2) {
        if(r1 > r2) {
            isPlayer_1_Turn = true;
            Toast.makeText(this, player_1.getName() +" begin", Toast.LENGTH_LONG).show();
        }
        else {
            isPlayer_1_Turn = false;
            Toast.makeText(this, player_2.getName() +" begin", Toast.LENGTH_LONG).show();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().hide(roll);
        transaction.commit();
        startTheGame();
    }
}