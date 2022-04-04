package com.example.serendipitouswords;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);


        // Fetch data that is passed from MainActivity
        Intent intent = getIntent();

        EditText editText = findViewById(R.id.editText);

        // Accessing the data using key and value
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        } else {

            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }

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

    //dabar saugo visada pastoviam faile, nesvarbu koks note.
    //persiraso vieni ant kitu notes
    //reikia sugalvot pagal ka sudaryt failo pavadinima
    public void save(View view){
        File dir = new File(getFilesDir(), "notes");
        EditText text = findViewById(R.id.editText);
        if (!dir.exists()){
            dir.mkdir();
        }
        try{
            File note = new File(dir, "example.md");
            FileWriter writer = new FileWriter(note);
            writer.write(text.getText().toString());
            writer.close();
            Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //užkrauna į teksto lauką duomenis iš example.md
    public void load(View view) {
        File dir = new File(getFilesDir(), "notes");
        File note = new File(dir, "example.md");
        EditText editText = findViewById(R.id.editText);
        Scanner sc = null;
        try {
            sc = new Scanner(note);
            StringBuilder buff = new StringBuilder();
            while(sc.hasNextLine()){
                buff.append(sc.nextLine());
            }
            sc.close();
            editText.setText(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
