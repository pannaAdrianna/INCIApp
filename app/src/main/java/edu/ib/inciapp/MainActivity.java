package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS INCI(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);

        readDataFromCSV();

    }


    public void onBtnLearnClick(View view) {
        Intent intent = new Intent(this, QuizINCIActivity.class);
        this.startActivity(intent);
    }

    public void onBtnOCRClick(View view) {
        Intent intent = new Intent(this, OCRActivity.class);
        this.startActivity(intent);
    }
    public void onBtnSearchIngredient(View view) {
        Intent intent = new Intent(this, CheckIngredientsActivity.class);
        this.startActivity(intent);
    }


    private void readDataFromCSV() {

        List<Flashcard> list = new ArrayList<>();

        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";

        try {
            reader.readLine();


            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity", "Line: " + line);

                String[] lineSplit = line.split(";");

                String label = lineSplit[0];
                String function = lineSplit[1];
                String description = lineSplit[2];


                String sqlIngredient = "INSERT OR REPLACE INTO INCI VALUES (?,?,?)";
                SQLiteStatement insertStatement = database.compileStatement(sqlIngredient);

                insertStatement.bindString(1, label);
                insertStatement.bindString(2, function);
                insertStatement.bindString(3, description);
                insertStatement.executeInsert();


                Flashcard temp = new Flashcard(label, function, description);
                list.add(temp);
                // Log the object
                Log.d("My Activity", "Just created: " + temp);
            }

        } catch (IOException e) {

            Log.wtf("MyActivity", "Error reading data file on line" + line, e);


            e.printStackTrace();
        }
    }


}