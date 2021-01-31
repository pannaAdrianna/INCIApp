package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class QuizINCIActivity extends AppCompatActivity {

    Button btnTrue;
    Button btnFalse;
    Button btnNext;
    private CardView card;

    private Flashcard[] deckOfFlashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_i_n_c_i);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        try {
            btnTrue.setOnClickListener(v -> {
                btnNext.setVisibility(View.VISIBLE);
            });
            btnFalse.setOnClickListener(v -> {
                btnNext.setVisibility(View.VISIBLE);
            });
            btnNext.setOnClickListener(v -> {
                Toast.makeText(this, "Next Question Please", LENGTH_LONG).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void onBtnNextClick(View view) {

    }
}