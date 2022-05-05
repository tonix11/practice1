package ru.mirea.gavrilin.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private final String FILE_NAME = "file_name";

    private SharedPreferences preferences;
    private EditText newFileName;
    private Button saveButton;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newFileName = findViewById(R.id.fileName);
        editText = findViewById(R.id.editText);

        saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(this::setSaveButton);

        preferences = getPreferences(MODE_PRIVATE);

        try {
            newFileName.setText(preferences.getString(FILE_NAME, "asd"));
            editText.setText(getTextFromFile());
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void setSaveButton(View view) {

        FileOutputStream outputStream;
        String string = editText.getText().toString();
        String name = newFileName.getText().toString();
        try {
            outputStream = openFileOutput(name, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
            Toast.makeText(this, "file saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FILE_NAME, name);
        editor.apply();
    }


    public String getTextFromFile() {
        FileInputStream inputStream = null;
        try {
            inputStream = openFileInput(newFileName.getText().toString());
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            Toast.makeText(this, "file loaded", Toast.LENGTH_SHORT).show();
            return text;
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}