package com.example.wagtailjournal;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

public class Oyaji {
    static final int APP_STORAGE_ACCESS_REQUEST_CODE = 1;

    private MainActivity activity;
    private Kakaa kakaa;
    private Musuko musuko;
    private Musume musume;
    private File directory = new File("/storage/emulated/0/Documents/notes");

    public Oyaji(MainActivity act, Kakaa ka, Musuko mk, Musume ms) {
        activity = act;
        kakaa = ka;
        musuko = mk;
        musume = ms;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void startup() {
        if (Environment.isExternalStorageManager()) {
            loadJournal();
            return;
        }
        requestPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void requestPermission() {
        Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
        Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
        activity.startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void receivePermission(int requestCode) {
        if (requestCode != APP_STORAGE_ACCESS_REQUEST_CODE)
            return;
        if (!Environment.isExternalStorageManager())
            Toast.makeText(activity, "permission denied", Toast.LENGTH_LONG).show();
        loadJournal();
    }

    public void newJournal(String text) {
        if (!saveJournal(text))
            return;
        musuko.updateTimestamp();
        activity.clear();
    }

    public boolean saveJournal(String text) {
        if (text.isEmpty())
            return false;
        File file = musuko.getFile(directory);
        try {
            kakaa.save(file, text);
            return true;
        } catch (IOException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void loadJournal() {
        try {
            File latest = musume.searchLatestJournal(directory);
            String text = kakaa.load(latest);
            musuko.updateTimestamp(latest);
            activity.update(text);
        } catch (IOException | ParseException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
