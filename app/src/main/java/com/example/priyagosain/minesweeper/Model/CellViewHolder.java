package com.example.priyagosain.minesweeper.Model;

import android.view.View;
import android.widget.ImageView;

import com.example.priyagosain.minesweeper.R;

/**
 * Created by lovish on 10/14/17.
 */

public class CellViewHolder {
    private ImageView cell;
    private ImageView shade;

    public CellViewHolder(View view) {
        this.cell = (ImageView) view.findViewById(R.id.displayCell);
        this.shade = (ImageView) view.findViewById(R.id.shade);
    }

    public ImageView getCell() {
        return cell;
    }

    public ImageView getShade() {
        return shade;
    }

}
