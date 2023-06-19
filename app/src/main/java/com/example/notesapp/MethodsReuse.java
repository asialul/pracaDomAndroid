package com.example.notesapp;

import android.content.Context;
import android.widget.Toast;

public class MethodsReuse {

    static void Tosty(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
