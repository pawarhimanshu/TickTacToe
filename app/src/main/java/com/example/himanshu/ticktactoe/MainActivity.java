package com.example.himanshu.ticktactoe;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {


    LinearLayout rootLayout;

    public static final String TAG = "MainActivity.class";

    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = 0;
    public static final int NO_PLAYER = -1;

    public static final int INCOMPLETE = 1;
    public static final int PLAYER_X_WON = 2;
    public static final int PLAYER_O_WON = 3;
    public static final int DRAW = 4;


    public int currentStatus;


    public int SIZE = 3;

    public ArrayList<LinearLayout> rows;
    public TTTButton[][] board;

    public int currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rootLayout = findViewById(R.id.rootLayout);
        setupBoard();

        Log.d(TAG,"On create called");


    }

    @Override
//    for orietation change retain screen
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    public void setupBoard() {
        currentStatus = INCOMPLETE;
        currentPlayer = PLAYER_O;
        rows = new ArrayList<>();
        board = new TTTButton[SIZE][SIZE];
        rootLayout.removeAllViews();

        for (int i = 0; i < SIZE; i++) {

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);

            rootLayout.addView(linearLayout);
            rows.add(linearLayout);
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                TTTButton button = new TTTButton(this);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(this);
                LinearLayout row = rows.get(i);
                row.addView(button);
                button.setTextSize(40);
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
                board[i][j] = button;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.resetItem){
            setupBoard();
        }else if(id == R.id.size3){
            SIZE = 3;
            setupBoard();
        }
        else if(id == R.id.size4){
            SIZE = 4;
            setupBoard();
        }
        else if(id == R.id.size5){
            SIZE = 5;
            setupBoard();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (currentStatus == INCOMPLETE) {
            TTTButton button = (TTTButton) view;
            button.setPlayer(currentPlayer);
            checkGameStatus();
            togglePlayer();
        }
    }

    private void checkGameStatus() {

        //ROWS
        for (int i = 0; i < SIZE; i++) {
            //Assume row has same players
            boolean rowSame = true;
            TTTButton firstButton = board[i][0];
            for (int j = 1; j < SIZE; j++) {
                TTTButton button = board[i][j];
                if (button.isEmtpy() || button.getPlayer() != firstButton.getPlayer()) {
                    rowSame = false;
                    break;
                }
            }

            if (rowSame) {
                int playerWon = firstButton.getPlayer();
                updateStatus(playerWon);
                return;
            }
        }


        //Cols
        for (int j = 0; j < SIZE; j++) {
            boolean colSame = true;
            TTTButton firstButton = board[0][j];
            for (int i = 0; i < SIZE; i++) {
                TTTButton button = board[i][j];
                if (button.isEmtpy() || button.getPlayer() != firstButton.getPlayer()) {
                    colSame = false;
                    break;
                }
            }
            if (colSame) {
                int playerWon = firstButton.getPlayer();
                updateStatus(playerWon);
                return;
            }

        }

        //First Diagonal
        boolean diagonalSame = true;
        TTTButton firstButton = board[0][0];
        for (int i = 0; i < SIZE; i++) {
            TTTButton button = board[i][i];
            if (button.isEmtpy() || button.getPlayer() != firstButton.getPlayer()) {
                diagonalSame = false;
                break;
            }
        }
        if (diagonalSame) {
            int playerWon = firstButton.getPlayer();
            updateStatus(playerWon);
            return;
        }

        //Second Diagonal
        diagonalSame = true;
        firstButton = board[0][SIZE - 1];
        for (int i = 0; i < SIZE; i++) {
            TTTButton button = board[i][SIZE - 1 - i];
            if (button.isEmtpy() || button.getPlayer() != firstButton.getPlayer()) {
                diagonalSame = false;
                break;
            }
        }
        if (diagonalSame) {
            int playerWon = firstButton.getPlayer();
            updateStatus(playerWon);
            return;
        }


        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                TTTButton button = board[i][j];

                if (button.isEmtpy()) {
                    return;
                }
            }
        }

        Toast.makeText(this, "Draw", Toast.LENGTH_LONG).show();
        currentStatus = DRAW;


    }

    public void updateStatus(int playerWon) {
        if (playerWon == PLAYER_X) {
            currentStatus = PLAYER_X_WON;
            Toast.makeText(this, "PLAYER X WON", Toast.LENGTH_LONG).show();
        } else if (playerWon == PLAYER_O) {
            currentStatus = PLAYER_O_WON;
            Toast.makeText(this, "PLAYER O WON", Toast.LENGTH_LONG).show();
        }
    }

    public void togglePlayer() {
        if (currentPlayer == PLAYER_O) {
            currentPlayer = PLAYER_X;
        } else {
            currentPlayer = PLAYER_O;
        }
    }
}
