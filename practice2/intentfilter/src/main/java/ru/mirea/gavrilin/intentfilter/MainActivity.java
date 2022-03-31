package ru.mirea.gavrilin.intentfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(view -> {
            Uri address = Uri.parse("https://www.mirea.ru/");
            Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
            try{
                startActivity(openLinkIntent);
            }
            catch (ActivityNotFoundException e){
                Log.d("Intent", "Не получается обработать намерение!");
            }
        });


        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(view -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Гаврилин Пётр Викторович");
            startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"));
        });

    }
}