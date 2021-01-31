package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizINCIActivity extends AppCompatActivity {

    private Button btnTrue;
    private Button btnFalse;
    private CardView card;

    private Flashcard[] deckOfFlashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_i_n_c_i);


    }


    public void onBtnFalseClick(View view) {

    }

    public void onBtnTrueClick(View view) {
    }

    public void onCardCLick(View view) {
        try {
            card.setCardBackgroundColor(Color.BLUE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}