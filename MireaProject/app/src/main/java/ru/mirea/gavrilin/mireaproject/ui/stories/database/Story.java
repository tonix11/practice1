package ru.mirea.gavrilin.mireaproject.ui.stories.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Story {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String name;
    private String date;
    private String word;

    public Story(String name, String date, String word) {
        this.name = name;
        this.date = date;
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getWord() {
        return word;
    }
}