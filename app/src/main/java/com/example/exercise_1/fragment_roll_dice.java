package com.example.exercise_1;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.Random;


public class fragment_roll_dice extends Fragment {

    protected View view;
    private  final Random rand = new Random();
    private Button game_BTN_roll_dice;
    private ImageView game_IMG_dice1;
    private ImageView game_IMG_dice2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(view==null){
            view = inflater.inflate(R.layout.fragment_roll_dice, container, false);
        }

        findViews(view);

        game_BTN_roll_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roll();
            }
        });

        return view;
    }

    private void roll() {

        int value1 = randomDiceValue();
        int value2 = randomDiceValue();

        int res1 = getResources().getIdentifier("dice_" + value1, "drawable",this.getActivity().getPackageName());
        int res2 = getResources().getIdentifier("dice_" + value2, "drawable",this.getActivity().getPackageName());

        game_IMG_dice1.setImageResource(res1);
        game_IMG_dice2.setImageResource(res2);

    }

    private int randomDiceValue() {
        return rand.nextInt(6) + 1;
    }

    private void findViews(View view) {

        game_BTN_roll_dice = view.findViewById(R.id.game_BTN_roll_dice);
        game_IMG_dice1 = view.findViewById(R.id.game_IMG_dice1);
        game_IMG_dice2 = view.findViewById(R.id.game_IMG_dice2);
    }

}
