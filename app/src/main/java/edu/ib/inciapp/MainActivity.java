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

/**
 * Main class for INCIApp
 * @author Adrianna Boczzr
 */
public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;
    List<Flashcard> ingredientsList = new ArrayList<>();
    List<Flashcard> preggo = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS ingredients(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        String sqlPreggo = "CREATE TABLE IF NOT EXISTS preggo(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);
        database.execSQL(sqlPreggo);

        readDataFromCSV();



    }

    /**
     * method moves user to OCRActivity
     * @see OCRActivity
     * @param view
     */
    public void onBtnOCRClick(View view) {
        Intent intent = new Intent(this, OCRActivity.class);
        this.startActivity(intent);
    }

    /**
     * method moves user to CheckIngredientsActivity
     * @see CheckIngredientsActivity
     * @param view
     */
    public void onBtnSearchIngredient(View view) {
        Intent intent = new Intent(this, CheckIngredientsActivity.class);
        this.startActivity(intent);
    }

    /**
     * method moves user to AnalyzeCosmeticActivity
     * @see AnalyzeCosmeticActivity
     * @param view
     */
    public void onBtnAnalyzeClick(View view) {
        Intent intent = new Intent(this, AnalyzeCosmeticActivity.class);
        this.startActivity(intent);
    }

    /**
     * method imports data from csv file (from res/raw folder) and adds it to SQLiteDatabase
     */
    private void readDataFromCSV() {

        //basic ingredients


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


                String sqlIngredient = "INSERT OR REPLACE INTO ingredients VALUES (?,?,?)";
                SQLiteStatement insertStatement = database.compileStatement(sqlIngredient);

                insertStatement.bindString(1, label);
                insertStatement.bindString(2, function);
                insertStatement.bindString(3, description);
                insertStatement.executeInsert();


                Flashcard temp = new Flashcard(label, function, description);
                ingredientsList.add(temp);
                // Log the object
                Log.d("My Activity", "Just created: " + temp);
            }
            reader.close();

        } catch (IOException e) {

            Log.wtf("MyActivity", "Error reading data file on line" + line, e);


            e.printStackTrace();
        }



        InputStream is2 = getResources().openRawResource(R.raw.preggo);
        BufferedReader reader2 = new BufferedReader(
                new InputStreamReader(is2, Charset.forName("UTF-8"))
        );


        try {
            reader2.readLine();

            while ((line = reader2.readLine()) != null) {
                Log.d("MyActivity", "Line: " + line);

                String[] lineSplit = line.split(";");

                String label = lineSplit[0];
                String function = lineSplit[1];
                String description = lineSplit[2];


                String sqlIngredient = "INSERT OR REPLACE INTO preggo VALUES (?,?,?)";
                SQLiteStatement insertStatement = database.compileStatement(sqlIngredient);

                insertStatement.bindString(1, label);
                insertStatement.bindString(2, function);
                insertStatement.bindString(3, description);
                insertStatement.executeInsert();


                Flashcard temp = new Flashcard(label, function, description);
                preggo.add(temp);
                // Log the object
                Log.d("Preggo", "Just created: " + temp);
            }
            reader2.close();

        } catch (IOException e) {

            Log.wtf("MyActivity", "Error reading data file on line" + line, e);


            e.printStackTrace();
        }



    }



}