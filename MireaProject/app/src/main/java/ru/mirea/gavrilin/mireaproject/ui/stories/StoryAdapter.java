package ru.mirea.gavrilin.mireaproject.ui.stories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.gavrilin.mireaproject.R;
import ru.mirea.gavrilin.mireaproject.ui.stories.database.Story;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Story> stories;

    StoryAdapter(Context context, List<Story> stories) {
        this.stories = stories;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.nameStory.setText(story.getName());
        holder.dateStory.setText(story.getDate());
        holder.wordStory.setText(story.getWord());
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameStory, dateStory, wordStory;
        ViewHolder(View view){
            super(view);
            nameStory = view.findViewById(R.id.nameStory);
            dateStory = view.findViewById(R.id.dateStory);
            wordStory = view.findViewById(R.id.wordStory);
        }
    }
}