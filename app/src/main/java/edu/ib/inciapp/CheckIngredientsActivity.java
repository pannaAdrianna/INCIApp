package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * class is responsible of showing controversial ingredients in lis view
 */
public class CheckIngredientsActivity extends AppCompatActivity {
    SQLiteDatabase database;
    private int lengthOfDeck;

    // initiate a Switch
    Switch switcher;
    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterPreggo;
    // check current state of a Switch (true or false).
    ArrayList<String> result = new ArrayList<>();
    ArrayList<String> resultPreggo = new ArrayList<>();

    /**
     * method shows list of controversial ingredients in lis view,
     * allows to search in list
     *
     * @param savedInstanceState existing instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ingredients);

        searchView = findViewById(R.id.search_bar);
        switcher = (Switch) findViewById(R.id.preggoSwitch);


        database = openOrCreateDatabase("INCIdb", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS ingredients(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlDB);

        String sqlCount = "SELECT count(*) FROM ingredients";
        Cursor cursor = database.rawQuery(sqlCount, null);
        cursor.moveToFirst();
        lengthOfDeck = cursor.getInt(0);
        String sqlPreggo = "CREATE TABLE IF NOT EXISTS preggo(Name VARCHAR PRIMARY KEY, Function VARCHAR, Description VARCHAR)";
        database.execSQL(sqlPreggo);
        cursor.close();

        Cursor c = database.rawQuery("SELECT Name, Function, Description FROM ingredients", null);
        Cursor c2 = database.rawQuery("SELECT Name, Function, Description FROM preggo", null);

        if (c.moveToFirst()) {

            do {
                String name = c.getString(c.getColumnIndex("Name"));
                String function = c.getString(c.getColumnIndex("Function"));
                String description = c.getString(c.getColumnIndex("Description"));

                result.add(name + ": " + function + " " + description);
                resultPreggo.add(name + ": " + function + " " + description);

            } while (c.moveToNext());
        }
        c.close();
        if (c2.moveToFirst()) {

            do {
                String name = c2.getString(c2.getColumnIndex("Name"));
                String function = c2.getString(c2.getColumnIndex("Function"));
                String description = c2.getString(c2.getColumnIndex("Description"));

                resultPreggo.add(name + ": " + function + " " + description);

            } while (c2.moveToNext());
        }
        c2.close();


        listView = (ListView) findViewById(R.id.listView);
        if (!switcher.isChecked())
            adapter = adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, result);
        else
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, resultPreggo);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(CheckIngredientsActivity.this, "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("onSwitchClicked");
                if (switcher.isChecked())
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, resultPreggo);
                else adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, result);
                listView.setAdapter(adapter);
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


    }





}