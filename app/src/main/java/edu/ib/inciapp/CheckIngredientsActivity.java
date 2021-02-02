package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CheckIngredientsActivity extends AppCompatActivity {
    SQLiteDatabase database;
    private int lengthOfDeck;
    List<Flashcard> list = new ArrayList<>();
    TextView tvDisplay = (TextView) findViewById(R.id.tvDisplay);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ingredients);

        List<Flashcard> list = new ArrayList<>();
        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS INCI(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM INCI";
        Cursor cursor = database.rawQuery(sqlCount, null);
        cursor.moveToFirst();
        lengthOfDeck = cursor.getInt(0);
        cursor.close();
    }



    public void onBtnExcelClick(View view) {
        readData();
    }


    private void readData() {


        InputStream is = getResources().openRawResource(R.raw.data);

        // Reads text from character-input stream, buffering characters for efficient reading
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        // Initialization
        String line = "";

        // Initialization
        try {
            // Step over headers
            reader.readLine();

            // If buffer is not empty
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity", "Line: " + line);
                // use comma as separator columns of CSV
                String[] lineSplit = line.split("\t");
                // Read the data
                String label = lineSplit[0];
                String function = lineSplit[1];
                String description = lineSplit[2];


                String sqlIngredient = "INSERT INTO INCI VALUES (?,?,?,?)";
                SQLiteStatement insertStatement = database.compileStatement(sqlIngredient);

                insertStatement.bindString(1, label);
                insertStatement.bindString(2, function);
                insertStatement.bindString(3, description);
                insertStatement.executeInsert();

                // Adding object to a class
                Flashcard temp = new Flashcard(label, function,description);
                list.add(temp);
                // Log the object
                Log.d("My Activity", "Just created: " + temp);
            }

        } catch (IOException e) {
            // Logs error with priority level
            Log.wtf("MyActivity", "Error reading data file on line" + line, e);

            // Prints throwable details
            e.printStackTrace();
        }
    }

}