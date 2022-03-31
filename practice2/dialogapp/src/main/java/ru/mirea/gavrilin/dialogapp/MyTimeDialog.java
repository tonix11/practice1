package ru.mirea.gavrilin.dialogapp;

import android.app.TimePickerDialog;
import android.content.Context;

public class MyTimeDialog extends TimePickerDialog {

    public MyTimeDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }
}
