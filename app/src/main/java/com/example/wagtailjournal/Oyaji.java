package com.example.wagtailjournal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

public class Oyaji {
    static final int APP_STORAGE_ACCESS_REQUEST_CODE = 1;

    private MainActivity activity;
    private Kakaa kakaa;
    private Musuko musuko;

    public Oyaji(MainActivity act, Kakaa ka, Musuko mk) {
        activity = act;
        kakaa = ka;
        musuko = mk;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void requestPermission(String text) {
        if (Environment.isExternalStorageManager()) {
            newJournal(text);
            return;
        }
        Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
        Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
        activity.startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
    }

    private void newJournal(String text) {
        if (text.isEmpty())
            return;
        if (!kakaa.save(musuko.getFile(), text)) {
            Toast.makeText(activity, "save failed", Toast.LENGTH_LONG).show();
            return;
        }
        musuko.updateTimestamp();
        activity.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void receivePermission(String text, int requestCode) {
        if (requestCode != APP_STORAGE_ACCESS_REQUEST_CODE)
            return;
        if (!Environment.isExternalStorageManager())
            Toast.makeText(activity, "permission denied", Toast.LENGTH_LONG).show();
        newJournal(text);
    }
}
