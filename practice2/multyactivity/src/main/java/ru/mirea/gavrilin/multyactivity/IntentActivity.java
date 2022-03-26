package ru.mirea.gavrilin.multyactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class IntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
    }

    public void click(View view){
        Intent intent = new Intent(this, IntentActivity2.class);
        intent.putExtra("name", ((EditText)findViewById(R.id.nameText)).getText().toString());
        startActivity(intent);
    }
}