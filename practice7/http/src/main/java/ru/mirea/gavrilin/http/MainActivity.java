package ru.mirea.gavrilin.http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView ipTextView;
    private TextView cityTextView;
    private TextView countryTextView;

    private final String url = "http://whatismyip.akamai.com/";

    private class DownloadPageTask extends AsyncTask<String, Void, Info>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ipTextView.setText("Loading...");
        }

        @Override
        protected Info doInBackground(String... strings) {
            return getInformationByIp();
        }

        @Override
        protected void onPostExecute(Info info) {
            ipTextView.setText(info.getIp());
            cityTextView.setText(info.getCity());
            countryTextView.setText(info.getCountry());
            super.onPostExecute(info);
        }

        private Info getInformationByIp(){
            try {
                String content = getContentFromApi("http://ip-api.com/json/",
                        "GET");
                JSONObject responseJson = new JSONObject(content);
                String country = String.valueOf(responseJson.get("country"));
                String city = String.valueOf(responseJson.get("city"));
                String ip = String.valueOf(responseJson.get("query"));
                return new Info(ip, city, country);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        private String getContentFromApi(String address, String method) throws IOException {
            InputStream inputStream = null;
            String data = "";
            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setReadTimeout(100000);
                connection.setConnectTimeout(100000);
                connection.setRequestMethod(method);
                connection.setInstanceFollowRedirects(true);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int read;
                    while ((read = inputStream.read()) != -1) {
                        bos.write(read);
                    }
                    bos.close();
                    data = bos.toString();
                } else {
                    data = connection.getResponseMessage() + " . Error Code : " + responseCode;
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return data;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipTextView = findViewById(R.id.ipAdd);
        cityTextView = findViewById(R.id.city);
        countryTextView = findViewById(R.id.country);
        Button getInfoButton = findViewById(R.id.buttonSV);
        getInfoButton.setOnClickListener(this::onGetInfoClick);
    }


    private void onGetInfoClick(View view){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = null;
        if (connectivityManager != null) {
            networkinfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkinfo != null && networkinfo.isConnected()) {
            new DownloadPageTask().execute(url);
        } else {
            Toast.makeText(this, "No Internet connection",
                    Toast.LENGTH_SHORT).show();
        }
    }
}