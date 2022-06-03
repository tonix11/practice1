package ru.mirea.gavrilin.mireaproject.ui.map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yandex.mapkit.geometry.Point;

import ru.mirea.gavrilin.mireaproject.R;

public class Dialog extends DialogFragment {
    private String title;
    private String message;
    private Point point;

    public Dialog(String title, String message, Point point) {
        this.title = title;
        this.message = message;
        this.point = point;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_location).setNeutralButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getDialog().cancel();
            }
        });
        return builder.create();
    }
}
