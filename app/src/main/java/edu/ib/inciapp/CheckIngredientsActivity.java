package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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


    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ingredients);

        searchView = findViewById(R.id.search_bar);


        List<Flashcard> list = new ArrayList<>();
        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS INCI(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM INCI";
        Cursor cursor = database.rawQuery(sqlCount, null);
        cursor.moveToFirst();
        lengthOfDeck = cursor.getInt(0);
        cursor.close();


        ArrayList<String> wyniki = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT Name, Function, Description FROM INCI", null);

        if (c.moveToFirst()) {

            do {
                String name = c.getString(c.getColumnIndex("Name"));
                String function = c.getString(c.getColumnIndex("Function"));
                String description = c.getString(c.getColumnIndex("Description"));

                wyniki.add(name + ": " + function + " " + description);

            } while (c.moveToNext());
        }

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, wyniki);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CheckIngredientsActivity.this, "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //ustawienie filtrów

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                CheckIngredientsActivity.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                CheckIngredientsActivity.this.adapter.getFilter().filter(newText);
                return false;
            }
        });


        c.close();


    }







}