package com.girtmobile.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 is green, 1 = red
    int activePlayer = 0;
    boolean gameActive = true;
    // 2 = unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPos = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};


    public void dropIn(View view){
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.c3);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.a3);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360f).setDuration(600);

            for(int[] winningP:winningPos){
                if(gameState[winningP[0]] == gameState[winningP[1]] &&
                        gameState[winningP[1]] == gameState[winningP[2]] &&
                        gameState[winningP[0]] != 2) {

                    String winner = "Red";

                    if (gameState[winningP[0]] == 0) {
                        winner = "Green";
                    }

                    //someone has won
                    gameActive = false;
                    TextView winnerMessage = (TextView) findViewById(R.id.winner);

                    winnerMessage.setText(winner + " has won!");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.lotPlayAgain);
                    layout.setVisibility(View.VISIBLE);
                } else {
                    boolean gameIsOver = true;
                    for(int counterState : gameState) {
                        if (counterState == 2) {
                            gameIsOver = false;
                        }
                    }

                    if (gameIsOver) {
                        TextView winnerMessage = (TextView) findViewById(R.id.winner);

                        winnerMessage.setText("It's A draw");

                        LinearLayout layout = (LinearLayout) findViewById(R.id.lotPlayAgain);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.lotPlayAgain);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for (int i = 0; i<gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grdLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
