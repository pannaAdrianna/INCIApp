package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


    Button btnNext;
    TextView tvFront;
    TextView tvBack;
    private int currentCard;
    private int lengthOfDeck;
    SQLiteDatabase database;
    List<Flashcard> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_i_n_c_i);

        btnNext = (Button) findViewById(R.id.btnNext);

        tvFront = (TextView) findViewById(R.id.tvName);
        tvBack = (TextView) findViewById(R.id.tvDescription);


        list = new ArrayList<>();
        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS INCI(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM INCI";
        Cursor cursor = database.rawQuery(sqlCount, null);
        cursor.moveToFirst();
        lengthOfDeck = cursor.getInt(0);
        cursor.close();
        currentCard= randomFlashcard(lengthOfDeck);


        if (lengthOfDeck == 0) {
          Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
         /*     String sqlIngredient = "INSERT OR REPLACE INTO INCI VALUES (?,?,?)";
            SQLiteStatement insertStatement = database.compileStatement(sqlIngredient);

            insertStatement.bindString(1, "Aqua");
            insertStatement.bindString(2, "funkcja");
            insertStatement.bindString(3, "brak zastrzeżeń");
            insertStatement.executeInsert();

            insertStatement.bindString(1, "Alanine");
            insertStatement.bindString(2, "funkcja");
            insertStatement.bindString(3, "Brak klasyfikacji jako substancja niebezpieczna zgodnie z przepisami chemicznymi");
            insertStatement.executeInsert();

            insertStatement.bindString(1, "So");
            insertStatement.bindString(2, "ffasas");
            insertStatement.bindString(3, "sakjhoshdoais");
            insertStatement.executeInsert();*/

        } else {

            Cursor c = database.rawQuery("SELECT Name, Function, Description FROM INCI", null);

            if (c.moveToFirst()) {

                do {
                    String name = c.getString(c.getColumnIndex("Name"));
                    String function = c.getString(c.getColumnIndex("Function"));
                    String description = c.getString(c.getColumnIndex("Description"));

                    Flashcard temp = new Flashcard(name, function, description);
                    list.add(temp);

                } while (c.moveToNext());

            }

            c.close();
            tvFront.setText(list.get(currentCard).getLabel());
            tvBack.setText(list.get(currentCard).getBackgroundDescrpition());


        }

    }

    private int randomFlashcard(int range) {
        Random random = new Random();
        Log.i("RANDOM", String.valueOf(currentCard));
        Log.i("length", String.valueOf(lengthOfDeck));
        return random.nextInt(range + 1);


    }


    public void onBtnNextClick(View view) {
        tvFront.setText(list.get(randomFlashcard(lengthOfDeck)).getLabel());
        tvBack.setText(list.get(randomFlashcard(lengthOfDeck)).getBackgroundDescrpition());

    }
}