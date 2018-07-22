package com.example.himanshu.ticktactoe;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;

public class TTTButton extends AppCompatButton {


    private int player = MainActivity.NO_PLAYER;


    public TTTButton(Context context) {
        super(context);

    }

    public void setPlayer(int player){
        this.player = player;
        if(player == MainActivity.PLAYER_X){
            setText("X");

        }else if(player == MainActivity.PLAYER_O){
            setText("O");
        }
        setEnabled(false);
    }

    public int getPlayer(){
        return this.player;
    }

    public boolean isEmtpy(){
        return this.player == MainActivity.NO_PLAYER;

//        if(this.player == MainActivity.NO_PLAYER){
//            return  true;
//        }
//        else {
//            return  false;
//        }
    }

}
