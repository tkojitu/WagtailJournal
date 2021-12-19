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

    private Activity activity;
    private Kakaa kakaa;

    public Oyaji(Activity act, Kakaa ka) {
        activity = act;
        kakaa = ka;
    }

    public String openLatest() {
        return kakaa.openLatest();
    }

    public void save(String text) {
        kakaa.save(text);
    }

    public void newJournal(String text) {
        kakaa.saveAndUpdate(text);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void requestPermission() {
        if (Environment.isExternalStorageManager())
            return;
        Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
        Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
        activity.startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void receivePermission(int requestCode) {
        if (requestCode != APP_STORAGE_ACCESS_REQUEST_CODE)
            return;
        if (Environment.isExternalStorageManager())  {
            Toast.makeText(activity, "permission granted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "permission denied", Toast.LENGTH_LONG).show();
        }
    }
}
