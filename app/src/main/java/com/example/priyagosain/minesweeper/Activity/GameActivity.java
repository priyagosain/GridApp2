package com.example.priyagosain.minesweeper.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priyagosain.minesweeper.Adapter.CustomGridAdapter;
import com.example.priyagosain.minesweeper.Model.Cell;
import com.example.priyagosain.minesweeper.Model.CellViewHolder;
import com.example.priyagosain.minesweeper.R;
import com.example.priyagosain.minesweeper.Utils.PrepareGrid;
import com.example.priyagosain.minesweeper.Utils.UncoverGrid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private GridView minegrid;
    private List<Cell> cellsData = new ArrayList<>();
    private static int GRID_ROWS = 9;
    private static int GRID_COLUMNS = 9;
    private CustomGridAdapter gridAdapter;
    private int numberOfMines = 10;
    private boolean alreadyClicked;
    private ImageButton emoji;
    private TextView secondsCount;
    private TextView mineCountDisplay;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private TextView bestScoreDisplay;
    private long timeElapsed;
    private Timer timer;
    private long bestScore;
    private ImageButton soundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putLong("timeBeforePause", 0);
        editor.commit();
        setContentView(R.layout.activity_game);
        bestScoreDisplay = (TextView) this.findViewById(R.id.bestScore);
        soundButton = (ImageButton) this.findViewById(R.id.soundButton);
        bestScore = sharedPref.getLong("bestScore", 0);
        if (bestScore == 0) {
            bestScoreDisplay.setText("XXX");
        } else {
            if(bestScore < 99)
            {
                bestScoreDisplay.setText("0" + bestScore);
            } else {
                bestScoreDisplay.setText(bestScore + "");
            }
        }
        String soundValue = sharedPref.getString("soundValue",null);
        if(soundValue == null) {
            editor.putString("soundValue", String.valueOf(Boolean.TRUE));
            editor.commit();
        } else if(soundValue.equalsIgnoreCase("true")) {
            soundButton.setImageDrawable(this.getResources().getDrawable(R.drawable.volumeon));
        } else if(soundValue.equalsIgnoreCase("false")) {
            soundButton.setImageDrawable(this.getResources().getDrawable(R.drawable.volumeoff));
        }
        numberOfMines = getIntent().getIntExtra("numberOfMines", 10);
        cellsData = PrepareGrid.prepareGridData(GRID_ROWS, GRID_COLUMNS, numberOfMines, cellsData);
        mineCountDisplay = (TextView) this.findViewById(R.id.minecount);
        mineCountDisplay.setText("0" + numberOfMines);
        secondsCount = (TextView) this.findViewById(R.id.secondCount);
        secondsCount.setText("000");
        emoji = (ImageButton) this.findViewById(R.id.emoji);
        emoji.setOnClickListener(this);
        minegrid = (GridView) this.findViewById(R.id.minegrid);
        gridAdapter = new CustomGridAdapter(this, cellsData);
        minegrid.setAdapter(gridAdapter);
        soundButton.setOnClickListener(this);
        minegrid.setOnItemClickListener(this);
        minegrid.setOnItemLongClickListener(this);
        Toast.makeText(this, "May the force be with you!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emoji: {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                break;
            }
            case R.id.soundButton: {
                String soundValue = sharedPref.getString("soundValue", "true");
                if (soundValue.equalsIgnoreCase("true")) {
                    editor.putString("soundValue", String.valueOf(Boolean.FALSE));
                    editor.commit();
                    soundButton.setImageDrawable(this.getResources().getDrawable(R.drawable.volumeoff));
                } else if (soundValue.equalsIgnoreCase("false")) {
                    editor.putString("soundValue", String.valueOf(Boolean.TRUE));
                    editor.commit();
                    soundButton.setImageDrawable(this.getResources().getDrawable(R.drawable.volumeon));
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
        CellViewHolder holder = (CellViewHolder) view.getTag();
        boolean hasWon = false;
        if (!alreadyClicked) {
            alreadyClicked = true;
            editor.putLong("startTime", new Date().getTime());
            editor.commit();
            startTimer();
        }
        List<Cell> cells = (List<Cell>) holder.getCell().getTag();
        if (!cells.get(position).isClicked() && cells.get(position).isMined()) {
            if(sharedPref.getString("soundValue", "true").equalsIgnoreCase("true")){
                mp.start();
            }
            minegrid.setOnItemClickListener(null);
            timer.cancel();
            vibe.vibrate(1000);
            for (Cell cell : cells) {
                if (cell.isMined())
                    cell.setUncovered(true);
            }
            emoji.setImageDrawable(
                    getApplicationContext().getResources().getDrawable(R.drawable.lose128));
            Toast.makeText(this, "You Lose!!!", Toast.LENGTH_LONG).show();
        } else if (!cells.get(position).isClicked()) {
            if(sharedPref.getString("soundValue", "true").equalsIgnoreCase("true")){
                mp.start();
            }
            vibe.vibrate(100);
            cells = UncoverGrid.uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, position);
        }
        if (checkWin(cells)) {
            minegrid.setOnItemClickListener(null);
            timer.cancel();
            vibe.vibrate(1000);
            emoji.setImageDrawable(
                    getApplicationContext().getResources().getDrawable(R.drawable.win128));
            if (sharedPref.getLong("bestScore", 0) == 0
                    || sharedPref.getLong("bestScore", 0) > timeElapsed) {
                editor.putLong("bestScore", timeElapsed);
                editor.commit();
            }
            Toast.makeText(this, "You Won!!!", Toast.LENGTH_LONG).show();
        }
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        editor.putLong("timeBeforePause", timeElapsed);
        editor.commit();
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        editor.putLong("startTime", new Date().getTime());
        editor.commit();
        if (alreadyClicked) {
            startTimer();

        }
    }

    private void startTimer() {
        if (alreadyClicked) {
            final long timerStartEpoch = sharedPref.getLong("startTime", 0);
            final long timeBeforePause = sharedPref.getLong("timeBeforePause", 0);
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                public void run() {
                    long currentTime = new Date().getTime();
                    timeElapsed = ((currentTime - timerStartEpoch) / 1000) + timeBeforePause;
                    String displayTime = timeElapsed + "";
                    if (displayTime.length() == 1) {
                        displayTime = "00" + displayTime;
                    } else if (displayTime.length() == 2) {
                        displayTime = "0" + displayTime;
                    }
                    final String time = displayTime;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            secondsCount.setText(time + "");
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        CellViewHolder holder = (CellViewHolder) view.getTag();
        List<Cell> cells = (List<Cell>) holder.getCell().getTag();
        if (!cells.get(i).isUncovered() && !cells.get(i).isFlagged()) {
            cells.get(i).setFlagged(true);
        } else if (!cells.get(i).isUncovered() && cells.get(i).isFlagged()) {
            cells.get(i).setFlagged(false);
        }
        gridAdapter.notifyDataSetChanged();
        return true;
    }

    private boolean checkWin(List<Cell> cells) {
        boolean hasWon = false;
        int coveredCount = 0;
        int uncoveredCount = 0;
        for (Cell cell : cells) {
            if (cell.isMined() && !cell.isUncovered())
                coveredCount++;
            if (!cell.isMined() && cell.isUncovered())
                uncoveredCount++;
        }
        if ((coveredCount + uncoveredCount) == 81) {
            hasWon = true;
        }
        return hasWon;
    }
}
