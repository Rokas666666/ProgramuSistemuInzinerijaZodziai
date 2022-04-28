package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Scanner;

// vyksta kai pasirenkamas kažkoks note iš main activity
public class NoteEditorActivity extends AppCompatActivity {
    int noteId; // rodo kelintas elementas array liste notes (iš main activity)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);


        // Fetch data that is passed from MainActivity
        Intent intent = getIntent();

        EditText editText = findViewById(R.id.editText);
        EditText nameText = findViewById(R.id.nameText);

        // Accessing the data using key and value
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
            nameText.setText(MainActivity.titles.get(noteId));
        } else {

            MainActivity.notes.add("");
            MainActivity.titles.add("");
            noteId = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
            MainActivity.arrayAdapter2.notifyDataSetChanged();

        }
        // čia reiktu parašyt kas vyksta keičiant name lauke tekstą
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.titles.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter2.notifyDataSetChanged();
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.titles", Context.MODE_PRIVATE);
                HashSet<String> set2 = new HashSet(MainActivity.titles);
                sharedPreferences.edit().putStringSet("titles", set2).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //kas vyksta kai keičiamas tekstas
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // add your code here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                // Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // add your code here
            }
        });
    }

    // metoda kviečia mygtukas save, iš čia galima matyd adresa kur notes saugomi
    //jie saugomi pagal ju vieta array liste, todėl deletinant reiktu pakeist pavadinimus
    // geriausia pavadinimus turėt note klasėj, bet su tuo man nepavyko padaryt
    // mygtukai ---->  res -> layout -> activity_note_editor.xml
    public void save(View view){
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Notes");
        EditText text = findViewById(R.id.editText);
        EditText name = findViewById(R.id.nameText);
        if (!dir.exists()){
            dir.mkdir();
        }

        try{
            File note = new File(dir, noteId + ".md");
            FileWriter writer = new FileWriter(note);
            writer.write(text.getText().toString());
            writer.write(name.getText().toString());
            writer.close();
            Toast.makeText(this, "File saved to: " + note.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // kviečia load mygtukas, užloadina teksta iš failo su numeriu, kuris rodo kelintas note array liste main activity
    public void load(View view) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Notes");
        File note = new File(dir, noteId + ".md");
        EditText editText = findViewById(R.id.editText);
        EditText nameText = findViewById(R.id.nameText);
        Scanner sc = null;
        try {
            sc = new Scanner(note);
            StringBuilder buff = new StringBuilder();
            while(sc.hasNextLine()){
                buff.append(sc.nextLine() + '\n');
            }
            sc.close();
            editText.setText(buff);
            nameText.setText("FFddddd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
