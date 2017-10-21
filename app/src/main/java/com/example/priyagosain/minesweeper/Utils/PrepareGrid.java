package com.example.priyagosain.minesweeper.Utils;

import com.example.priyagosain.minesweeper.Model.Cell;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrepareGrid {
    public static List<Cell> prepareGridData(int GRID_ROWS, int GRID_COLUMNS, int numberOfMines, List<Cell> cellsData) {
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
//                  Log.i("MineInfo", cell.getCellNumber() + " " + cell.getX() + " " + cell.getY());
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
}
