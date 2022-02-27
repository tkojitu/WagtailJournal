package com.github.tkojitu.wagtailjournal;

import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Oyaji {

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

    private boolean mkdirNote() {
        if (directory.exists())
            return true;
        if (!directory.mkdir()) {
            Toast.makeText(activity, "cannot mkdir note", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void newJournal(String text) {
        if (!saveJournal(text))
            return;
        musuko.updateTimestampByFile();
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

    public void loadJournal() {
        if (!mkdirNote())
            return;
        try {
            File latest = musume.searchLatestJournal(directory);
            String text = kakaa.load(latest);
            musuko.updateTimestampByFile(latest);
            activity.update(text);
        } catch (IOException | ParseException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
