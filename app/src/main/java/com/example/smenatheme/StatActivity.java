package com.example.smenatheme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        SharedPreferences sharedPreferences = getSharedPreferences("game_data", MODE_PRIVATE);

        // Retrieve statistics from SharedPreferences
        int Wins = sharedPreferences.getInt("Wins", 0);
        int Losses = sharedPreferences.getInt("Losses", 0);
        int Draws = sharedPreferences.getInt("Draws", 0);

        TextView nolik = findViewById(R.id.nolikiTV);
        TextView krestiki = findViewById(R.id.krestikiTV);
        TextView draws = findViewById(R.id.nechyaTV);

        //String nol = Integer.toString(getIntent().getExtras().getInt("noliki"));
        //String krest = Integer.toString(getIntent().getExtras().getInt("krestiki"));
        //String draw = Integer.toString(getIntent().getExtras().getInt("Draw"));

        nolik.setText("Победы игрока: " + Wins);
        krestiki.setText("Поражения игрока: " + Losses);
        draws.setText("Ничьи игрока: " + Draws);

        Button backBtm = findViewById(R.id.nazadBtm);
        backBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
    }
}
