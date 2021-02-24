package com.inga11bil.mytictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button [] buttons = new Button[9];
    private Button newGame;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    boolean activePlayer;

    //player 1 =>0
    //player 2 => 1
    //empty => 2

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {
            {0,1,2}, {3,4,5} ,{6,7,8}, //rows

            {0,3,6}, {1,4,7}, {2,5,8} , //columns

            {0,4,8}, {2,4,6}
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore =  (TextView) findViewById(R.id.playerOneScore);

        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);

        playerStatus = (TextView)findViewById(R.id.playerStatus);

        newGame = (Button) findViewById(R.id.newGame);

        for(int i = 0; i< buttons.length; i++)
        {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());

            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;


    }

    @Override
    public void onClick(View v) {
       // Log.i("test", "button is clicked");    checking if the button is clickable

        if(!((Button)v).getText().toString().equals(""))
        {
            return;
        }

        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt( buttonID.substring(buttonID.length() - 1, buttonID.length()));

        if(activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#e0e884"));
            gameState[gameStatePointer] = 0;
        }
        else
        {
            ((Button) v).setText("0");
            ((Button) v).setTextColor(Color.parseColor("#e394db"));
            gameState[gameStatePointer] = 1;

        }

        rountCount++;

        if (checkWinner())
        {
            if(activePlayer)
            {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                newGame();
            }

            else
            {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                newGame();
            }

        }

        else if (rountCount == 9)
        {
            newGame();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();

        }

        else
        {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount > playerTwoScoreCount)
        {
            playerStatus.setText("Player One is Winning!");
        }
        else if (playerTwoScoreCount > playerOneScoreCount)
        {
            playerStatus.setText("Player Two is Winning!");
        }
        else
        {
            playerStatus.setText("");
        }

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();

                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");

                updatePlayerScore();
            }
        });



    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition : winningPositions)
        {
            if( gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2  )
            {
                winnerResult = true;
            }
        }

        return winnerResult;

    }

    public void updatePlayerScore()
    {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText((Integer.toString(playerTwoScoreCount)));

    }

    public void newGame()
    {
        rountCount = 0;
        activePlayer = true;

        for(int i = 0; i< buttons.length; i++)
        {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}