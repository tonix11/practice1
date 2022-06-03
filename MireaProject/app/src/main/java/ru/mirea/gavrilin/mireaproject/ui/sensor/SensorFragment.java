package ru.mirea.gavrilin.mireaproject.ui.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ru.mirea.gavrilin.mireaproject.R;


public class SensorFragment extends Fragment {
    private ListView listCountSensor;
    private SensorManager sensorManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor, container, false);

        listCountSensor = (ListView) view.findViewById(R.id.listView);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        HashMap<String, Object> sensorTypeList;
        for (int i = 0; i < 3; i++) {
            sensorTypeList = new HashMap<>();
            int random = ThreadLocalRandom.current().nextInt(sensors.size());
            sensorTypeList.put("Name", sensors.get(random).getName());
            sensorTypeList.put("Value", sensors.get(random).getMaximumRange());
            arrayList.add(sensorTypeList);
        }

        SimpleAdapter mHistory =
                new SimpleAdapter(view.getContext(), arrayList, android.R.layout.simple_list_item_2,
                        new String[]{"Name", "Value"},
                        new int[]{android.R.id.text1, android.R.id.text2});
        listCountSensor.setAdapter(mHistory);

        return view;
    }
}