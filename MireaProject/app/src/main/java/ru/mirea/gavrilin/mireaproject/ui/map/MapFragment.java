package ru.mirea.gavrilin.mireaproject.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.runtime.image.ImageProvider;

import java.util.List;

import ru.mirea.gavrilin.mireaproject.R;
import ru.mirea.gavrilin.mireaproject.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {
    private FragmentMapBinding binding;
    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private String MAPKIT_API_KEY = "34e130d3-7abe-41fe-b0c2-7328026d23b4";

    private boolean isWork = false;
    private static final int REQUEST_CODE_PERMISSION =100;
    private final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(requireContext());
        binding = FragmentMapBinding.inflate(inflater,container,false);

        mapView = binding.mapview;

        isWork = hasPermissions(requireActivity(), PERMISSIONS);
        if (isWork) {
            loadUserLocationLayer();
        } else {
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    PERMISSIONS, REQUEST_CODE_PERMISSION
            );
        }
        return binding.getRoot();
    }

    void loadUserLocationLayer(){
        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        mapView.getMap().move(
                new CameraPosition(new Point(55.794292, 37.701564), 8.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0F),
                null
        );

        addPlacemark(new Point(55.794292, 37.701564), R.drawable.ic_location,
                "ул. Стромынка, 20, Москва",
                "Название: МГУПИ\nКоординаты: 55.794292, 37.701564\nДата основания:1936 г.");

        addPlacemark(new Point(55.661733, 37.477908), R.drawable.ic_location,
                "просп. Вернадского, 86, Москва",
                "Название: МИТХТ\nКоординаты: 55.661733, 37.477908\nДата основания:1 июля 1900 г.");

        addPlacemark(new Point(55.669803, 37.479429), R.drawable.ic_location,
                "просп. Вернадского, 78, Москва",
                "Название: МИРЭА\nКоординаты: 55.669803, 37.479429\nДата основания: 1947 г.");

        addPlacemark(new Point(55.731457, 37.574415), R.drawable.ic_location,
                "Малая Пироговская улица, 1с5, Москва",
                "Название: МИРЭА\nКоординаты: 55.731457, 37.574415\nДата основания: 1 июля 1900 г.");

        addPlacemark(new Point(55.764925, 37.742172), R.drawable.ic_location,
                "5-я улица Соколиной Горы, 22, Москва",
                "Название: МИРЭА\nКоординаты: 55.764925, 37.742172\nДата основания: 1 июля 1900 г.");

        addPlacemark(new Point(45.052221, 41.912577), R.drawable.ic_location,
                "проспект Кулакова, 8литА, Ставрополь",
                "Название: Филиал в г. Ставрополе\nКоординаты: 45.052221, 41.912577\nДата основания: 18 декабря 1996 г.");

        addPlacemark(new Point(55.966766, 38.050778), R.drawable.ic_location,
                "Вокзальная ул., 2А, кор п. 61, Фрязино",
                "Название: Филиал в г. Фрязино\nКоординаты: 55.966766, 38.050778\nДата основания: 1962 г.");
    }

    private void addPlacemark(Point point, Integer image, String dialogTitle, String dialogText){
        PlacemarkMapObject placemark = mapView.getMap().getMapObjects().addPlacemark(point);
        placemark.setIcon(ImageProvider.fromBitmap(getBitmap(requireContext(), image)));

        placemark.addTapListener((mapObject, pointPlace) -> {
            new Dialog(dialogTitle, dialogText, point).show(requireFragmentManager(),"1");
            return true;
        });
    }

    private boolean hasPermissions(Context context, String[] permissions) {
        for(String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    public static Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }
}