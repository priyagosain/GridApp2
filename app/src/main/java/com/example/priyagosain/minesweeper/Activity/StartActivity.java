package com.example.priyagosain.minesweeper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.priyagosain.minesweeper.R;

/**
 * Created by lovish on 10/15/17.
 */

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button beginner = (Button) this.findViewById(R.id.beginner);
        Button intermediate = (Button) this.findViewById(R.id.intermediate);
        Button advanced = (Button) this.findViewById(R.id.advanced);
        beginner.setOnClickListener(this);
        intermediate.setOnClickListener(this);
        advanced.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.beginner: {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("numberOfMines", 10);
                startActivity(intent);
            }
                break;
            case R.id.intermediate: {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("numberOfMines", 16);
                startActivity(intent);
                break;
            }
            case R.id.advanced:{
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("numberOfMines", 22);
                startActivity(intent);
                break;
            }
        }
    }
}
