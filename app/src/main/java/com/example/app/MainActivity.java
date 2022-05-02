package com.example.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;

// šitas vyksta paleidus programėlę
public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>(); // listas note tekstui saugoti
    static ArrayList<String> titles = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static ArrayAdapter arrayAdapter2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_note) {

            // Going from MainActivity to NotesEditorActivity
            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //iš shared preferences išimami note tekstai
        ListView listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("com.example.titles", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        HashSet<String> set2 = (HashSet<String>) sharedPreferences2.getStringSet("titles", null);
        System.out.println("DEBUGGING: "+ getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE));

        // jei nebuvo jokiu note sukuriamas example note, kitu atveju užkrauna notes tekstus
        if (set == null) {
            notes.add("Example note");
            System.out.println("SET(NOTES) IS NULL");
        } else {
            notes = new ArrayList(set);
            System.out.println("SET(NOTES) IS NOT NULL");
        }

        if (set2 == null) {
            titles.add("Example title");
            System.out.println("SET2(TITLES) IS NULL");
        } else {
            titles = new ArrayList(set2);
            System.out.println("SET2(TITLES) IS NOT NULL");
        }

        // Using custom listView Provided by Android Studio
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notes);
        arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, titles);

        listView.setAdapter(arrayAdapter);
        listView.setAdapter(arrayAdapter2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Going from MainActivity to NotesEditorActivity
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });


        // čia deletinimas, bet jis trina tik iš array listo, pagal tutorial darytas pradžioj ir nekeistas
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;
                // To delete the data from the App
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemToDelete);
                                titles.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();
                                arrayAdapter2.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                                SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("com.example.titles", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(MainActivity.notes);
                                HashSet<String> set2 = new HashSet(MainActivity.titles);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                                sharedPreferences2.edit().putStringSet("titles", set2).apply();
                            }
                        }).setNegativeButton("No", null).show();
                return true;
            }
        });
    }
}
