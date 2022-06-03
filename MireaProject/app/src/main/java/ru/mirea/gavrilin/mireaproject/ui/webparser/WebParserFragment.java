package ru.mirea.gavrilin.mireaproject.ui.webparser;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import ru.mirea.gavrilin.mireaproject.databinding.FragmentWebParserBinding;


public class WebParserFragment extends Fragment {
    private FragmentWebParserBinding binding;
    private String host = "time-b.nist.gov";
    private int port = 13;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWebParserBinding.inflate(inflater,container,false);
        updateInfo(binding.getRoot());
        return binding.getRoot();
    }

    private void updateInfo(View view){
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String time = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine();
                time = reader.readLine();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.textView9.setText(time);
        }
    }

}