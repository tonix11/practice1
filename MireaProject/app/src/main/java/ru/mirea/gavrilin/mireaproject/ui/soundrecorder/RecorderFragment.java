package ru.mirea.gavrilin.mireaproject.ui.soundrecorder;

import static ru.mirea.gavrilin.mireaproject.ui.camera.CameraFragment.hasPermissions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ru.mirea.gavrilin.mireaproject.MainActivity;
import ru.mirea.gavrilin.mireaproject.R;
import ru.mirea.gavrilin.mireaproject.databinding.FragmentRecorderBinding;

public class RecorderFragment extends Fragment {

    private FragmentRecorderBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 100;
    final String TAG = MainActivity.class.getSimpleName();
    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    private boolean isWork = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecorderBinding.inflate(inflater,container,false);


        checkPermissions(binding.getRoot());
        setRecorder();
        return binding.getRoot();
    }

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private File audioFile;
    private Button recordBtn;
    private Button listenBtn;

    private void setRecorder(){
        mediaRecorder = new MediaRecorder();
        mediaPlayer = new MediaPlayer();

        recordBtn = binding.recordButton;
        recordBtn.setText("▶");
        recordBtn.setOnClickListener(this::recordClick);

        listenBtn = binding.listenButton;
        listenBtn.setOnClickListener(this::listenClick);
        listenBtn.setText("▶");
    }

    private void recordClick(View view) {
        Button btn = (Button) view;
        switch (btn.getText().toString()){
            case "▶":
                btn.setText("◼");
                listenBtn.setEnabled(false);
                listenBtn.requestFocus();
                try {
                    startRecording();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "◼":
                btn.setText("▶");
                listenBtn.setEnabled(true);
                mediaPlayer.stop();
                mediaRecorder.reset();
                mediaRecorder.release();
                break;
        }
    }

    private void listenClick(View view){
        Button btn = (Button) view;
        switch (btn.getText().toString()){
            case "▶":
                btn.setText("◼");
                recordBtn.setEnabled(false);
                recordBtn.requestFocus();
                try {
                    startListening();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "◼":
                btn.setText("▶");
                recordBtn.setEnabled(true);
                mediaPlayer.stop();
                break;
        }
    }

    private void startRecording() throws IOException {
        mediaRecorder.release();
        mediaRecorder = new MediaRecorder();
        if(Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
                Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()){
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            audioFile = new File(
                    requireContext().getExternalFilesDir(
                            Environment.DIRECTORY_MUSIC
                    ), "mirea.3gp"
            );
            System.out.println(audioFile);
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.start();
        }
    }

    private void startListening() throws IOException {
        mediaPlayer.release();
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setDataSource(audioFile.getPath());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void checkPermissions(View view){
        int storagePermissionStatus = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(storagePermissionStatus == PackageManager.PERMISSION_GRANTED) isWork = true;
        else ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION);

        isWork = hasPermissions(view.getContext(), Arrays.asList(PERMISSIONS));
        if (!isWork) {
            ActivityCompat.requestPermissions(
                    requireActivity(), PERMISSIONS,
                    REQUEST_CODE_PERMISSION);
        }
    }

    private boolean hasPermissions(Context context, List<String> permissions) {
        for(String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

}