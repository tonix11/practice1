package ru.mirea.gavrilin.dialogapp;

import android.app.DatePickerDialog;
import android.content.Context;

public class MyDateDialog extends DatePickerDialog{

    public MyDateDialog(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
        super(context,listener,year,month,day);
    }
}
