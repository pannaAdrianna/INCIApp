package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckIngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ingredients);
    }


    public void display(String value) {
        TextView tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        tvDisplay.setText(value);
    }

    public void onBtnExcelClick(View view) {
    }
}