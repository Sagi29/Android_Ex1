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
import androidx.fragment.app.FragmentManager;

import java.util.Random;


public class fragment_roll_dice extends Fragment {

    protected View view;
    private  final Random rand = new Random();
    private Button game_BTN_roll_dice;
    private ImageView game_IMG_dice1;
    private ImageView game_IMG_dice2;

    private int value1;
    private int value2;
    private CallBack_GameActivity callBack_gameActivity;



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
                Log.d("ptt","IN FRAGMANT !!!!!   value1 = "+ value1+"value2 = "+value2);
                if(value1 != value2)
                    getResult();

            }
        });

        return view;
    }

    private void getResult() {
        if (callBack_gameActivity != null) {
            callBack_gameActivity.rollResult(value1,value2);
        }
        else
            Log.d("ptt","callBack_gameActivity is NULL   !!!!! ");
        this.onHiddenChanged(false);
    }

    private void roll() {

        value1 = randomDiceValue();
        value2 = randomDiceValue();

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

    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onStop();
        }
    }

    public void setActivityCallBack(CallBack_GameActivity callBack_gameActivity){
        this.callBack_gameActivity = callBack_gameActivity;
    }
}
