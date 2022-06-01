package ru.mirea.gavrilin.practice7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private TextView timeView;
    private String host = "time-b.nist.gov";
    private int port = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeView = findViewById(R.id.timeView);
    }

    public void getTimeClick(View view) {
        GetTimeTask timeTask = new GetTimeTask();
        timeTask.execute();
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String timeResult = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine();
                timeResult = reader.readLine();
                Log.d(TAG,timeResult);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return timeResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            timeView.setText(result);
        }
    }
}