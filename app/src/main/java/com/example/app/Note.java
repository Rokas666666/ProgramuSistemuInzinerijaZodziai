package com.example.app;

import android.os.Environment;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;


// visa klasė tik pabandymui, kaip galėtų atrodyt, ji niekur nenaudojama, nes nepavyko pritaikyti
public class Note {
    private int ID;
    private String Name = "";
    private String FileName;
    //private String Password = "";
    //private boolean HasPassword = false;

    public Note(int id){
        this.ID = id;
        this.Name = "";
        this.FileName = createFileName();
        //this.Password = "";
        //this.HasPassword = false;
    }
    public void setName(String name){
        this.Name = name;
    }
    public String getFileName(){
        return this.FileName;
    }
    private String createFileName(){
        if (!Name.isEmpty()){
            return Name + ".md";
        }
        else{
            String fileName = ID + ".md";
            return fileName;
        }
    }
    public void renameFile(String fileName){
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Notes");
        if (dir.exists()){
            File from = new File(dir, this.FileName);
            File to = new File(dir, fileName);
            if (from.exists()){
                from.renameTo(to);
            }
        }
    }

    public void save(EditText text){
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Notes");
        if (!dir.exists()){
            dir.mkdir();
        }
        try{
            File note = new File(dir, this.FileName);
            FileWriter writer = new FileWriter(note);
            writer.write(text.getText().toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Notes");
        File note = new File(dir, this.FileName);
        Scanner sc = null;
        StringBuilder buff = new StringBuilder();
        try {
            sc = new Scanner(note);
            while(sc.hasNextLine()){
                buff.append(sc.nextLine());
            }
            sc.close();
            return buff.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return buff.toString();
    }

    @Override
    public String toString(){
        return Name;
    }
}
