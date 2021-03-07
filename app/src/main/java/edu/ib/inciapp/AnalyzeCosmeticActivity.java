package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Class analyze cosmetic by given text
 */
public class AnalyzeCosmeticActivity extends AppCompatActivity {
    EditText etIngredients;
    Button btnAnalyzeButton;
    TextView tvResult;
    Switch switcher;

    SQLiteDatabase database;
    List<Flashcard> ingredientList;
    List<Flashcard> preggoList;

    boolean flag;

    /**
     * method reads data from existing Table and creates Flashcard List object
     *
     * @param savedInstanceState
     * @see MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_cosmetic);


        etIngredients = (EditText) findViewById(R.id.etINCIstr);
        btnAnalyzeButton = (Button) findViewById(R.id.btnAnalyze);
        tvResult = (TextView) findViewById(R.id.tvControversialngriedients);
        switcher = findViewById(R.id.preggoSwitch);


        tvResult.setText("");

        Intent intent = getIntent();
        String message = intent.getStringExtra(OCRActivity.MESSAGE);
        etIngredients.setText(message);

        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS ingredients(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM ingredients";
        Cursor cursor = database.rawQuery(sqlCount, null);
        cursor.moveToFirst();
        cursor.close();
        ingredientList = new ArrayList<>();
        preggoList = new ArrayList<>();

        Cursor c = database.rawQuery("SELECT Name, Function, Description FROM ingredients", null);
        Cursor c2 = database.rawQuery("SELECT Name, Function, Description FROM preggo", null);

        if (c.moveToFirst()) {

            do {
                String name = c.getString(c.getColumnIndex("Name"));
                String function = c.getString(c.getColumnIndex("Function"));
                String description = c.getString(c.getColumnIndex("Description"));

                ingredientList.add(new Flashcard(name, function, description));
//                preggoList.add(new Flashcard(name, function, description));


            } while (c.moveToNext());
        }
        c.close();
        if (c2.moveToFirst()) {

            do {
                String name = c2.getString(c2.getColumnIndex("Name"));
                String function = c2.getString(c2.getColumnIndex("Function"));
                String description = c2.getString(c2.getColumnIndex("Description"));

                preggoList.add(new Flashcard(name, function, description));
            } while (c2.moveToNext());
        }
        c2.close();


    }

    /**
     * method reads text from editText, analyze and returns result in a textView (the controversial ones)
     * uses data drom database
     *
     * @param view current view
     */
    public void onBtnAnalyzeClick(View view) {
        List<String> results = new ArrayList<>();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        flag = false;

        if (TextUtils.isEmpty(etIngredients.getText())) {
            Toast.makeText(this, "Add ingredients to verify", Toast.LENGTH_SHORT).show();
            tvResult.setText("");
        } else {
            String[] strToAnalyze = etIngredients.getText().toString().replaceAll(" ", "").trim().split(",");

            try {

                for (String ingredient : strToAnalyze
                ) {
                    for (Flashcard cmpIng :
                            ingredientList) {
                        if (ingredient.equalsIgnoreCase(cmpIng.getLabel().replaceAll(" ", "").trim())) {
                            results.add(cmpIng.getLabel());
                        }
                    }
                    if (switcher.isChecked()) {
                        for (Flashcard cmpIng :
                                preggoList) {
                            if (ingredient.equalsIgnoreCase(cmpIng.getLabel().replaceAll(" ", "").trim())) {
                                if(!results.contains(cmpIng.getLabel())) results.add(cmpIng.getLabel());
                            }

                        }


                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder resultt = new StringBuilder();
            for (String str : results) resultt.append(str + ", ");
            tvResult.setText(resultt);

        }
    }


}