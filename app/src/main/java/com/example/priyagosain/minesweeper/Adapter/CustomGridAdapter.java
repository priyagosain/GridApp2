package com.example.priyagosain.minesweeper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.priyagosain.minesweeper.Model.Cell;
import com.example.priyagosain.minesweeper.R;
import com.example.priyagosain.minesweeper.Model.CellViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CustomGridAdapter extends BaseAdapter {
    private Context mainActivityContext;
    private List<Cell> cellsData = new ArrayList<>();
    LayoutInflater inflater;

    public CustomGridAdapter(Context context, List<Cell> cellsData) {
        this.mainActivityContext = context;
        this.cellsData = cellsData;
        inflater =
                (LayoutInflater) mainActivityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cellsData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        CellViewHolder holder = null;
        if (row == null) {
            row = inflater.inflate(R.layout.cell, null);
            holder = new CellViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (CellViewHolder) row.getTag();
        }

        if (cellsData.get(i).getMineCount() == 1) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.one32));
        } else if (cellsData.get(i).getMineCount() == 2) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.two32));
        } else if (cellsData.get(i).getMineCount() == 3) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.three32));
        } else if (cellsData.get(i).getMineCount() == 4) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.four32));
        } else if (cellsData.get(i).getMineCount() == 5) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.five32));
        } else if (cellsData.get(i).getMineCount() == 6) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.six32));
        } else if (cellsData.get(i).getMineCount() == 7) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.seven32));
        } else if (cellsData.get(i).getMineCount() == 8) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.eight32));
        }

        if (cellsData.get(i).isFlagged()) {
            holder.getShade()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.flag32));
        } else {
            holder.getShade()
                    .setImageDrawable(null);
        }

        if (cellsData.get(i).isMined()) {
            holder.getCell()
                    .setImageDrawable(mainActivityContext.getResources().getDrawable(R.drawable.mine32));
        }

        if (cellsData.get(i).isUncovered()) {
            holder.getShade().setVisibility(View.INVISIBLE);
        }
        holder.getCell().setTag(cellsData);
        return row;
    }
}

