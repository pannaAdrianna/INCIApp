package edu.ib.inciapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView) findViewById(R.id.textView);

/*
        String path = "in.xslx";
        File file = new File(path);
        List<Flashcard> flashcardList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);
            String line;
            int counter = 0;

            while ((line = bf.readLine()) != null) {
                String[] lineSplit = line.split("\t");
                String definition = lineSplit[0];
                String label = lineSplit[1];


                Flashcard tempFlashcard = new Flashcard(counter, definition, label);
                flashcardList.add(tempFlashcard);
                counter++;
            }
            bf.close();
            fileReader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }*/

//        tv.setText(flashcardList.get(0).getDefinition());


    }

    public void onBtnLearnClick(View view) {
        Intent intent = new Intent(this, QuizINCIActivity.class);
        this.startActivity(intent);
    }
}