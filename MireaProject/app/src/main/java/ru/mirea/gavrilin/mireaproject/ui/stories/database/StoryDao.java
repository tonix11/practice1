package ru.mirea.gavrilin.mireaproject.ui.stories.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StoryDao {
    @Query("SELECT * FROM story")
    List<Story> getAll();

    @Query ("SELECT * FROM story WHERE id = :id")
    Story getById(long id);

    @Insert
    void insert(Story item);
    @Update
    void update(Story item);
    @Delete
    void delete(Story item);
}