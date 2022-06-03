package ru.mirea.gavrilin.mireaproject.ui.stories;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mirea.gavrilin.mireaproject.databinding.FragmentStoryBinding;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.App;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.AppDatabase;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.Story;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.StoryDao;

public class StoryFragment extends Fragment {
    private FragmentStoryBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStoryBinding.inflate(inflater,container,false);
        setList();
        binding.floatingActionButton.setOnClickListener(this::onClick);
        return binding.getRoot();
    }

    private RecyclerView list;
    private ArrayList<Story> stories;
    private StoryDao storyDao;
    private AppDatabase appDatabase;

    private void setList(){
        appDatabase = App.instance.getDatabase();
        storyDao = appDatabase.storyDao();
        setInitialData();

        list = binding.recyclerView;
        StoryAdapter adapter = new StoryAdapter(getContext(), stories);
        binding.recyclerView.setAdapter(adapter);
    }

    private void setInitialData() {
        stories = (ArrayList<Story>) storyDao.getAll();
    }

    void onClick(View view) {
        Intent intent = new Intent(view.getContext(), StoryView.class);
        startActivity(intent);
    }

}