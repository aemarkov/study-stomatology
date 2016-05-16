package com.example.stomatologyclient.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Garrus on 16.05.2016.
 */
public class Helpers
{
    /**
     * Показывает простое сообщение, без обработки нажатий
     * @param context
     * @param title
     * @param text
     */
    public static void ShowMessage(Context context, String title, String text,DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("OK", listener);
        builder.setTitle(title);
        builder.setMessage(text);
        builder.show();
    }

    public static void ShowMessage(Context context, String title, String text)
    {
        ShowMessage(context, title, text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
}
