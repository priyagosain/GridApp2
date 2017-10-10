package com.example.priyagosain.gridapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public GridView mygrid;
    public String[] items = new String[81];
    TextView clickedItem;
    CustomGridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mygrid = (GridView) this.findViewById(R.id.mygrid);
        for (int i = 0; i < 81; i++) {
            items[i] = Integer.toString(i + 1);
        }
        gridAdapter = new CustomGridAdapter(this, items);
        mygrid.setAdapter(gridAdapter);

        mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedItem = (TextView) view.findViewById(R.id.textview);
            }
        });
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);

        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);

        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(this);

        Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(this);

        Button button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(this);

        Button button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Button button1 = (Button) v.findViewById(R.id.button1);
                String text1 = (String) button1.getText();
                clickedItem.setText(text1);
                break;
            case R.id.button2:
                Button button2 = (Button) v.findViewById(R.id.button2);
                String text2 = (String) button2.getText();
                clickedItem.setText(text2);
                break;
            case R.id.button3:
                Button button3 = (Button) v.findViewById(R.id.button3);
                String text3 = (String) button3.getText();
                clickedItem.setText(text3);
                break;
            case R.id.button4:
                Button button4 = (Button) v.findViewById(R.id.button4);
                String text4 = (String) button4.getText();
                clickedItem.setText(text4);
                break;
            case R.id.button5:
                Button button5 = (Button) v.findViewById(R.id.button5);
                String text5 = (String) button5.getText();
                clickedItem.setText(text5);
                break;
            case R.id.button6:
                Button button6 = (Button) v.findViewById(R.id.button6);
                String text6 = (String) button6.getText();
                clickedItem.setText(text6);
                break;
            case R.id.button7:
                Button button7 = (Button) v.findViewById(R.id.button7);
                String text7 = (String) button7.getText();
                clickedItem.setText(text7);
                break;
            case R.id.button8:
                Button button8 = (Button) v.findViewById(R.id.button8);
                String text8 = (String) button8.getText();
                clickedItem.setText(text8);
                break;
            case R.id.button9:
                Button button9 = (Button) v.findViewById(R.id.button9);
                String text9 = (String) button9.getText();
                clickedItem.setText(text9);
                break;
        }
    }
}