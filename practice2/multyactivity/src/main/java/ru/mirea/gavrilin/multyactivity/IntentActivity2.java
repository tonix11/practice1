package ru.mirea.gavrilin.multyactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class IntentActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent2);
        TextView text = findViewById(R.id.nameTextView);

        text.setText(getIntent().getSerializableExtra("name").toString());
    }
}