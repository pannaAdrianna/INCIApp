package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.widget.Toast.LENGTH_LONG;

public class QuizINCIActivity extends AppCompatActivity {

    Button btnTrue;
    Button btnFalse;
    Button btnNext;
    TextView tvFront;
    TextView tvBack;
    private int currentCard;
    private int lengthOfDeck;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_i_n_c_i);

        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);
        tvFront = (TextView) findViewById(R.id.tvName);
        tvBack = (TextView) findViewById(R.id.tvDescription);

        List<Flashcard> list = new ArrayList<>();
        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS INCI(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM INCI";
        Cursor cursor = database.rawQuery(sqlCount, null);
        cursor.moveToFirst();
        lengthOfDeck = cursor.getInt(0);
        cursor.close();

        if (lengthOfDeck == 0) {
            Toast.makeText(this, "Add data", Toast.LENGTH_LONG).show();
            String sqlIngredient = "INSERT INTO INCI VALUES (?,?,?)";
            SQLiteStatement insertStatement = database.compileStatement(sqlIngredient);

            insertStatement.bindString(1, "Aqua");
            insertStatement.bindString(2, "funkcja");
            insertStatement.bindString(3, "brak zastrzeżeń");
            insertStatement.executeInsert();

            insertStatement.bindString(1, "Alanine");
            insertStatement.bindString(2, "funkcja");
            insertStatement.bindString(3, "Brak klasyfikacji jako substancja niebezpieczna zgodnie z przepisami chemicznymi");
            insertStatement.executeInsert();
            lengthOfDeck = list.size();


        } else {
            lengthOfDeck = list.size();
            Log.i("Number of Flashcards", String.valueOf(lengthOfDeck));
            randomFlashcard();

            Cursor c = database.rawQuery("SELECT DISTINCT Name, Function, Description FROM INCI", null);

            if (c.moveToFirst()) {

                do {
                    String name = c.getString(c.getColumnIndex("Name"));
                    String function = c.getString(c.getColumnIndex("Function"));
                    String description = c.getString(c.getColumnIndex("Description"));

                    Flashcard temp = new Flashcard(name, function, description);
                    list.add(temp);

                } while (c.moveToNext());

            }

            tvFront.setText(list.get(0).getLabel());
            tvBack.setText(list.get(0).getBackgroundDescrpition());
            c.close();


        }


        try {
            btnTrue.setOnClickListener(v -> {
                btnNext.setVisibility(View.VISIBLE);
            });
            btnFalse.setOnClickListener(v -> {
                btnNext.setVisibility(View.VISIBLE);
            });
            btnNext.setOnClickListener(v -> {
                Toast.makeText(this, "Next Question Please", LENGTH_LONG).show();
                randomFlashcard();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void randomFlashcard() {
        Random random = new Random();
        currentCard = random.nextInt(lengthOfDeck + 1);
    }


    public void onBtnNextClick(View view) {

    }
}