package com.example.priyagosain.minesweeper.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putLong("timeBeforePause", 0);
        editor.commit();
        setContentView(R.layout.activity_game);
        bestScoreDisplay = (TextView) this.findViewById(R.id.bestScore);
        bestScore = sharedPref.getLong("bestScore",0);
        if(bestScore == 0)
        {
            bestScoreDisplay.setText("XXX");
        } else {
            bestScoreDisplay.setText(bestScore+"");
        }
        numberOfMines = getIntent().getIntExtra("numberOfMines", 10);
        cellsData = prepareGridData();
        mineCountDisplay = (TextView) this.findViewById(R.id.minecount);
        mineCountDisplay.setText("0"+numberOfMines);
        secondsCount = (TextView) this.findViewById(R.id.secondCount);
        secondsCount.setText("000");
        emoji = (ImageButton) this.findViewById(R.id.emoji);
        emoji.setOnClickListener(this);
        minegrid = (GridView) this.findViewById(R.id.minegrid);
        gridAdapter = new CustomGridAdapter(this, cellsData);
        minegrid.setAdapter(gridAdapter);
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
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        CellViewHolder holder = (CellViewHolder) view.getTag();
        boolean hasWon = false;
        if(!alreadyClicked){
            alreadyClicked = true;
            editor.putLong("startTime",new Date().getTime());
            editor.commit();
            startTimer();
        }
        List<Cell> cells = (List<Cell>) holder.getCell().getTag();
        if (!cells.get(position).isClicked() && cells.get(position).isMined()) {
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
            vibe.vibrate(100);
            cells = uncoverCells(cells, position);
        }
        if (checkWin(cells)) {
            minegrid.setOnItemClickListener(null);
            timer.cancel();
            vibe.vibrate(1000);
            emoji.setImageDrawable(
                    getApplicationContext().getResources().getDrawable(R.drawable.win128));
            editor.putLong("bestScore",timeElapsed);
            editor.commit();
            Toast.makeText(this, "You Won!!!", Toast.LENGTH_LONG).show();
        }
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        editor.putLong("timeBeforePause", timeElapsed);
        editor.commit();
        if(timer != null) timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        editor.putLong("startTime",new Date().getTime());
        editor.commit();
        if (alreadyClicked) {
            startTimer();

        }
    }

    private void startTimer() {
        if(alreadyClicked) {
            final long timerStartEpoch = sharedPref.getLong("startTime", 0);
            final long timeBeforePause = sharedPref.getLong("timeBeforePause", 0);
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                public void run() {
                    long currentTime = new Date().getTime();
                    timeElapsed = ((currentTime - timerStartEpoch) / 1000) + timeBeforePause;
                    String displayTime = timeElapsed + "";
                    if(displayTime.length()==1){
                        displayTime = "00" + displayTime;
                    }else if(displayTime.length()==2){
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
        } else if(!cells.get(i).isUncovered() && cells.get(i).isFlagged()){
            cells.get(i).setFlagged(false);
        }
        gridAdapter.notifyDataSetChanged();
        return true;
    }

    private List<Cell> prepareGridData() {
        Set<Integer> randomNumbers = new HashSet<>();
        while (randomNumbers.size() < numberOfMines) {
            randomNumbers.add(new Random().nextInt(GRID_ROWS * GRID_COLUMNS));
        }
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int column = 0; column < GRID_COLUMNS; column++) {
                Cell cell = new Cell();
                cell.setX(row);
                cell.setY(column);
                cell.setCellNumber((row * GRID_ROWS) + column);
                if (randomNumbers.contains(cell.getCellNumber())) {
                    cell.setMined(true);
                    Log.i("MineInfo", cell.getCellNumber() + " " + cell.getX() + " " + cell.getY());
                }
                cellsData.add(cell);
            }
        }
        for (int i = 0; i < GRID_ROWS * GRID_COLUMNS; i++) {
            Cell cell = cellsData.get(i);
            int cellNumber = cellsData.get(i).getCellNumber();
            if (cell.isMined()) {
                // updates 8 neighbor's
                if ((cell.getX() > 0 && cell.getX() < GRID_ROWS - 1) && cell.getY() > 0
                        && cell.getY() < GRID_COLUMNS - 1) {
                    cellsData.get(cellNumber - GRID_ROWS - 1)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber - GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber - GRID_ROWS + 1)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS + 1).getMineCount()) + 1);
                    cellsData.get(cellNumber - 1)
                            .setMineCount((cellsData.get(cellNumber - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + 1)
                            .setMineCount((cellsData.get(cellNumber + 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS - 1)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS + 1)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS + 1).getMineCount()) + 1);
                }
                // updates  3 neighbor's
                else if (cell.getX() == 0 && cell.getY() == 0) {
                    cellsData.get(cellNumber + 1)
                            .setMineCount((cellsData.get(cellNumber + 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS + 1)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS + 1).getMineCount()) + 1);
                }
                // updates 3 neighbor's
                else if (cell.getX() == 0 && cell.getY() == GRID_COLUMNS - 1) {
                    cellsData.get(cellNumber - 1)
                            .setMineCount((cellsData.get(cellNumber - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS - 1)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS).getMineCount()) + 1);
                }
                // updates 3 neighbor's
                else if (cell.getX() == GRID_ROWS - 1 && cell.getY() == 0) {
                    cellsData.get(cellNumber - GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber - GRID_ROWS + 1)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS + 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + 1)
                            .setMineCount((cellsData.get(cellNumber + 1).getMineCount()) + 1);
                }
                // updates 3 neighbor's
                else if (cell.getX() == GRID_ROWS - 1 && cell.getY() == GRID_COLUMNS - 1) {
                    cellsData.get(cellNumber - GRID_ROWS - 1)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber - GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber - 1)
                            .setMineCount((cellsData.get(cellNumber - 1).getMineCount()) + 1);
                }
                // updates 5 neighbor's
                else if (cell.getX() == 0 && cell.getY() > 0 && cell.getY() < GRID_COLUMNS - 1) {
                    cellsData.get(cellNumber - 1)
                            .setMineCount((cellsData.get(cellNumber - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + 1)
                            .setMineCount((cellsData.get(cellNumber + 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS - 1)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS + 1)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS + 1).getMineCount()) + 1);
                }
                // updates 5 neighbor's
                else if (cell.getX() == GRID_ROWS - 1 && cell.getY() > 0
                        && cell.getY() < GRID_COLUMNS - 1) {
                    cellsData.get(cellNumber - GRID_ROWS - 1)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber - GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber - GRID_ROWS + 1)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS + 1).getMineCount()) + 1);
                    cellsData.get(cellNumber - 1)
                            .setMineCount((cellsData.get(cellNumber - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + 1)
                            .setMineCount((cellsData.get(cellNumber + 1).getMineCount()) + 1);
                }
                // updates 5 neighbor's
                else if (cell.getY() == 0 && cell.getX() > 0 && cell.getX() < GRID_ROWS - 1) {
                    cellsData.get(cellNumber - GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber - GRID_ROWS + 1)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS + 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + 1)
                            .setMineCount((cellsData.get(cellNumber + 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS + 1)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS + 1).getMineCount()) + 1);
                }
                // updates 5 neighbor's
                else if (cell.getY() == GRID_COLUMNS - 1 && cell.getX() > 0
                        && cell.getX() < GRID_ROWS - 1) {
                    cellsData.get(cellNumber - GRID_ROWS - 1)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber - GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber - GRID_ROWS).getMineCount()) + 1);
                    cellsData.get(cellNumber - 1)
                            .setMineCount((cellsData.get(cellNumber - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS - 1)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS - 1).getMineCount()) + 1);
                    cellsData.get(cellNumber + GRID_ROWS)
                            .setMineCount((cellsData.get(cellNumber + GRID_ROWS).getMineCount()) + 1);
                }
            }
        }
        return cellsData;
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

    private List<Cell> uncoverCells(List<Cell> cells, int position) {
        Cell cell = cells.get(position);
        int cellNumber = cells.get(position).getCellNumber();
        if ((cell.getX() > 0 && cell.getX() < GRID_ROWS - 1) && cell.getY() > 0
                && cell.getY() < GRID_COLUMNS - 1 && !cell.isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber - GRID_ROWS - 1).isMined() || cells.get(cellNumber - GRID_ROWS)
                            .isMined() || cells.get(cellNumber - GRID_ROWS + 1).isMined() || cells
                            .get(cellNumber - 1).isMined() || cells.get(cellNumber + 1).isMined() || cells
                            .get(cellNumber + GRID_ROWS - 1).isMined() || cells.get(cellNumber + GRID_ROWS)
                            .isMined() || cells.get(cellNumber + GRID_ROWS + 1).isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS - 1);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS + 1);
                cells = uncoverCells(cells, cellNumber - 1);
                cells = uncoverCells(cells, cellNumber + 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS - 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS + 1);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        // updates  3 neighbor's
        else if (cell.getX() == 0 && cell.getY() == 0 && !cell.isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber + 1).isMined() || cells.get(cellNumber + GRID_ROWS).isMined()
                            || cells.get(cellNumber + GRID_ROWS + 1).isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber + 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS + 1);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        // updates 3 neighbor's
        else if (cell.getX() == 0 && cell.getY() == GRID_COLUMNS - 1 && !cell.isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber - 1).isMined() || cells.get(cellNumber + GRID_ROWS - 1).isMined()
                            || cells.get(cellNumber + GRID_ROWS).isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber - 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS - 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        // updates 3 neighbor's
        else if (cell.getX() == GRID_ROWS - 1 && cell.getY() == 0 && !cell.isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber - GRID_ROWS).isMined() || cells.get(cellNumber - GRID_ROWS + 1)
                            .isMined() || cells.get(cellNumber + 1).isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS + 1);
                cells = uncoverCells(cells, cellNumber + 1);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        // updates 3 neighbor's
        else if (cell.getX() == GRID_ROWS - 1 && cell.getY() == GRID_COLUMNS - 1 && !cell
                .isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber - GRID_ROWS - 1).isMined() || cells.get(cellNumber - GRID_ROWS)
                            .isMined() || cells.get(cellNumber - 1).isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS - 1);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(cells, cellNumber - 1);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        // updates 5 neighbor's
        else if (cell.getX() == 0 && cell.getY() > 0 && cell.getY() < GRID_COLUMNS - 1 && !cell
                .isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber - 1).isMined() || cells.get(cellNumber + 1).isMined() || cells
                            .get(cellNumber + GRID_ROWS - 1).isMined() || cells.get(cellNumber + GRID_ROWS)
                            .isMined() || cells.get(cellNumber + GRID_ROWS + 1).isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber - 1);
                cells = uncoverCells(cells, cellNumber + 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS - 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS + 1);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        // updates 5 neighbor's
        else if (cell.getX() == GRID_ROWS - 1 && cell.getY() > 0 && cell.getY() < GRID_COLUMNS - 1
                && !cell.isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber - GRID_ROWS - 1).isMined() || cells.get(cellNumber - GRID_ROWS)
                            .isMined() || cells.get(cellNumber - GRID_ROWS + 1).isMined() || cells
                            .get(cellNumber - 1).isMined() || cells.get(cellNumber + 1).isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS - 1);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS + 1);
                cells = uncoverCells(cells, cellNumber - 1);
                cells = uncoverCells(cells, cellNumber + 1);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        // updates 5 neighbor's
        else if (cell.getY() == 0 && cell.getX() > 0 && cell.getX() < GRID_ROWS - 1 && !cell
                .isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber - GRID_ROWS).isMined() || cells.get(cellNumber - GRID_ROWS + 1)
                            .isMined() || cells.get(cellNumber + 1).isMined() || cells.get(cellNumber + GRID_ROWS)
                            .isMined() || cells.get(cellNumber + GRID_ROWS + 1).isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS + 1);
                cells = uncoverCells(cells, cellNumber + 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS + 1);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        // updates 5 neighbor's
        else if (cell.getY() == GRID_COLUMNS - 1 && cell.getX() > 0 && cell.getX() < GRID_ROWS - 1
                && !cell.isUncovered()) {
            boolean hasMinedNeighbor =
                    cells.get(cellNumber - GRID_ROWS - 1).isMined() || cells.get(cellNumber - GRID_ROWS)
                            .isMined() || cells.get(cellNumber - 1).isMined() || cells
                            .get(cellNumber + GRID_ROWS - 1).isMined() || cells.get(cellNumber + GRID_ROWS)
                            .isMined();
            if (!hasMinedNeighbor) {
                cell.setUncovered(true);
                cell.setClicked(true);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS - 1);
                cells = uncoverCells(cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(cells, cellNumber - 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS - 1);
                cells = uncoverCells(cells, cellNumber + GRID_ROWS);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        return cells;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("nitesh","onStop");
    }
}
