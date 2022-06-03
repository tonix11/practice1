package ru.mirea.gavrilin.mireaproject.ui.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ru.mirea.gavrilin.mireaproject.MainActivity;
import ru.mirea.gavrilin.mireaproject.R;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.App;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.AppDatabase;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.Story;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.StoryDao;


public class StoryView extends AppCompatActivity {

    private EditText nameEditStory;
    private EditText dateEditStory;
    private EditText wordEditStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_view);

        nameEditStory = findViewById(R.id.nameEditStory);
        dateEditStory = findViewById(R.id.dateEditStory);
        wordEditStory = findViewById(R.id.wordEditStory);
    }

    public void saveBtn(View view) {
        AppDatabase db = App.getInstance().getDatabase();
        StoryDao StoryDao = db.storyDao();

        StoryDao.insert(new Story(nameEditStory.getText().toString(),
                                dateEditStory.getText().toString(),
                                wordEditStory.getText().toString()));

        nameEditStory.setText("");
        dateEditStory.setText("");
        wordEditStory.setText("");

        Intent intent = new Intent(this, StoryFragment.class);
        startActivity(intent);
    }
}