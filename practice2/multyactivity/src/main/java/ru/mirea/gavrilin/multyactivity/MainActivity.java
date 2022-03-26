package ru.mirea.gavrilin.multyactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void clickIntent(View view){
        startActivity(new Intent(this,IntentActivity.class));
    }

    public void clickBundle(View view){
        startActivity(new Intent(this, BundleActivity.class));
    }
}