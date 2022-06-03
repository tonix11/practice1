package ru.mirea.gavrilin.mireaproject.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.SeekBar;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.mirea.gavrilin.mireaproject.R;
import ru.mirea.gavrilin.mireaproject.databinding.FragmentSettingsBinding;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private SharedPreferences preferences;
    private CalendarView calendar;
    private SeekBar seekBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater,container,false);
        calendar = binding.calendarView;
        seekBar = binding.seekBar2;

        preferences = requireActivity().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);

        seekBar.setProgress(preferences.getInt("seek",0));
        calendar.setDate(preferences.getLong("calendar", System.currentTimeMillis()));
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("seek", seekBar.getProgress());
                editor.apply();
                return false;
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                SharedPreferences.Editor editor = preferences.edit();
                Calendar calend = new GregorianCalendar(y,m,d);
                editor.putLong("calendar", calend.getTime().getTime());
                editor.apply();
            }
        });


        return binding.getRoot();
    }
}