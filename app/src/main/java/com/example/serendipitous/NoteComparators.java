package com.example.serendipitous;

import java.util.ArrayList;
import java.util.Comparator;

public class NoteComparators {
    public ArrayList<Comparator<Note>> ComparatorList = new ArrayList<>();
    public ArrayList<String> ComparatorNameList = new ArrayList<>();

    public NoteComparators()
    {
        ComparatorList.add(titleOrder);
        ComparatorList.add(reverseTitleOrder);
        ComparatorList.add(dateOrder);
        ComparatorList.add(reverseDateOrder);

        ComparatorNameList.add("Sorted by titles");
        ComparatorNameList.add("Sorted by titles reversed");
        ComparatorNameList.add("Sorted by dates");
        ComparatorNameList.add("Sorted by dates reversed");
    }

    public int getCount()
    {
        return ComparatorList.size();
    }

    Comparator<Note> titleOrder =  new Comparator<Note>() {
        public int compare(Note n1, Note n2) {
            return n1.title.compareTo(n2.title);
        }
    };

    Comparator<Note> reverseTitleOrder =  new Comparator<Note>() {
        public int compare(Note n1, Note n2) {
            return n1.title.compareTo(n2.title) * -1;
        }
    };

    Comparator<Note> dateOrder =  new Comparator<Note>() {
        public int compare(Note n1, Note n2) {
            return n1.lastSave.compareTo(n2.lastSave);
        }
    };

    Comparator<Note> reverseDateOrder =  new Comparator<Note>() {
        public int compare(Note n1, Note n2) {
            return n1.lastSave.compareTo(n2.lastSave) * -1;
        }
    };
}
