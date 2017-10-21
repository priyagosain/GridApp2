package com.example.priyagosain.minesweeper.Utils;

import com.example.priyagosain.minesweeper.Model.Cell;
import java.util.List;

public class UncoverGrid {
    public static List<Cell> uncoverCells(int GRID_ROWS, int GRID_COLUMNS, List<Cell> cells, int position){
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS + 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS + 1);
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS + 1);
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS);
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS + 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + 1);
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - 1);
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS + 1);
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS + 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + 1);
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS + 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS + 1);
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
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - GRID_ROWS);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS - 1);
                cells = uncoverCells(GRID_ROWS, GRID_COLUMNS, cells, cellNumber + GRID_ROWS);
            } else {
                cell.setUncovered(true);
                cell.setClicked(true);
            }
        }
        return cells;
    }
}
