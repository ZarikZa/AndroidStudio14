package com.example.smenatheme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    private static final int PLAYER_X = 1;
    private static final int PLAYER_O = 2;
    private static final int EMPTY = 0;

    private int currentPlayer = PLAYER_X;
    private int[][] board;
    private Button[][] buttons = new Button[3][3];

    SharedPreferences sharedPreferences;

    private TextView playerTurnTV;

    private int Wins = 0;
    private int Losses = 0;
    private int Draws = 0;

    private int gameMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        sharedPreferences = getSharedPreferences("game_data", Context.MODE_PRIVATE);
        buttons[0][0] = findViewById(R.id.button00);
        buttons[0][1] = findViewById(R.id.button01);
        buttons[0][2] = findViewById(R.id.button02);
        buttons[1][0] = findViewById(R.id.button10);
        buttons[1][1] = findViewById(R.id.button11);
        buttons[1][2] = findViewById(R.id.button12);
        buttons[2][0] = findViewById(R.id.button20);
        buttons[2][1] = findViewById(R.id.button21);
        buttons[2][2] = findViewById(R.id.button22);

        playerTurnTV = findViewById(R.id.playerTurnTV);

        board = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (board[row][col] == EMPTY) {
                            handlePlayerTurn(row, col);
                        }
                    }
                });
            }
        }
        Button statPereh = findViewById(R.id.statBtm);
        statPereh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayActivity.this, StatActivity.class);
                loadStats();
                saveStats();
                startActivity(intent);
            }
        });

        Button back = findViewById(R.id.backBtm);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button playWithBot = findViewById(R.id.playWithBot);
        playWithBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgainstBot();
            }
        });

        Button playWithFriend = findViewById(R.id.playWithDrug);
        playWithFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgainstFriend();
            }
        });

        resetGame();
    }

    private void playAgainstBot() {
        gameMode = 0;
        resetGame();
        currentPlayer = PLAYER_X;
        playerTurnTV.setText("Ход игрока X");
    }

    private void playAgainstFriend() {
        gameMode = 1;
        resetGame();
        currentPlayer = PLAYER_X;
        playerTurnTV.setText("Ход игрока X");
    }

    private void botTurn() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_O;
                    if (checkWin()) {
                        board[i][j] = PLAYER_O;
                        buttons[i][j].setText("O");
                        Toast.makeText(this, "Бот победил!", Toast.LENGTH_SHORT).show();
                        disableButtons();
                        return;
                    }
                    board[i][j] = EMPTY;

                    board[i][j] = PLAYER_X;
                    if (checkWin()) {
                        board[i][j] = PLAYER_O;
                        buttons[i][j].setText("O");
                        currentPlayer = PLAYER_X;
                        playerTurnTV.setText("Ход игрока X");
                        return;
                    }
                    board[i][j] = EMPTY;
                }
            }
        }

        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != EMPTY);

        board[row][col] = PLAYER_O;
        buttons[row][col].setText("O");
        currentPlayer = PLAYER_X;
        playerTurnTV.setText("Ход игрока X");
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] != EMPTY && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return true;
            }
        }

        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return true;
        }

        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
                buttons[i][j].setText("");
            }
        }
        currentPlayer = PLAYER_X;
        playerTurnTV.setText("Ход игрока X");
        enableButtons();
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void enableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private void handlePlayerTurn(int row, int col) {
        board[row][col] = currentPlayer;
        buttons[row][col].setText(currentPlayer == PLAYER_X ? "X" : "O");

        if (checkWin()) {
            Toast.makeText(this, "Игрок " + (currentPlayer == PLAYER_X ? "X" : "O") + " победил!", Toast.LENGTH_SHORT).show();
            if (currentPlayer == PLAYER_X){
                Losses++;
            }else {
                Wins++;
            }
            saveStats();
            disableButtons();
        } else {
            if (checkDraw()) {
                Toast.makeText(this, "Ничья!", Toast.LENGTH_SHORT).show();
                Draws++;
                saveStats();
                disableButtons();
            } else {
                currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
                playerTurnTV.setText("Ход игрока " + (currentPlayer == PLAYER_X ? "X" : "O"));
                if (gameMode == 0 && currentPlayer == PLAYER_O) {
                    botTurn();
                }
            }
        }
    }

    private void saveStats() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Wins", Wins);
        editor.putInt("Losses", Losses);
        editor.putInt("Draws", Draws);
        editor.apply();
    }

    private void loadStats() {
        Wins = sharedPreferences.getInt("Wins", 0);
        Losses = sharedPreferences.getInt("Losses", 0);
        Draws = sharedPreferences.getInt("Draws", 0);
    }
}