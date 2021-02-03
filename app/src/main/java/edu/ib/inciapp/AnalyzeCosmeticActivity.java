package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeCosmeticActivity extends AppCompatActivity {
    EditText etIngredients;
    Button btnAnalyzeButton;
    TextView tvResult;
    SQLiteDatabase database;
    List<Flashcard> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_cosmetic);

        etIngredients = (EditText) findViewById(R.id.etINCIstr);
        btnAnalyzeButton = (Button) findViewById(R.id.btnAnalyze);
        tvResult = (TextView) findViewById(R.id.tvControversialngriedients);


        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS INCI(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM INCI";
        Cursor cursor = database.rawQuery(sqlCount, null);
        cursor.moveToFirst();
//        lengthOfDeck = cursor.getInt(0);
        cursor.close();
        list = new ArrayList<>();

        Cursor c = database.rawQuery("SELECT Name, Function, Description FROM INCI", null);

        if (c.moveToFirst()) {

            do {
                String name = c.getString(c.getColumnIndex("Name"));
                String function = c.getString(c.getColumnIndex("Function"));
                String description = c.getString(c.getColumnIndex("Description"));
                Flashcard tempFlashcard = new Flashcard(name, function, description);

                list.add(tempFlashcard);

            } while (c.moveToNext());
        }


    }


    public void onBtnAnalyzeClick(View view) {

        if (TextUtils.isEmpty(etIngredients.getText()))
            Toast.makeText(this, "Add ingredients to verify", Toast.LENGTH_SHORT).show();
        else {
            List<String> results = new ArrayList<>();
            int counter = 0;
            boolean flag=false;

            try {
                String[] strToAnalyze = etIngredients.toString().trim().split(",");
                for (String ingredient : strToAnalyze) {

                   for(int i=0; i<strToAnalyze.length;i++){
                       Log.i("flag", String.valueOf(flag));
                       if (ingredient.equals(list.get(i).getLabel().trim())) {
                           flag=true;
                           counter++;
                       }
                   }
                }

                tvResult.setText(String.valueOf(flag));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}