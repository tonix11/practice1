package ru.mirea.gavrilin.lifedata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements androidx.lifecycle.Observer<Long> {
    private TextView networkNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkNameTextView = findViewById(R.id.textView);
        TimeController.getTime().observe(this, this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TimeController.setTime();
            }
        }, 5000);
    }

    @Override
    public void onChanged(@Nullable Long s) {
        Log.d(MainActivity.class.getSimpleName(), s + "");
        networkNameTextView.setText("" + s);
    }
}