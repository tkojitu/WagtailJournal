package com.example.wagtailjournal;

import java.io.File;

public class Kakaa {
    private static File directory;
    private Musuko musuko;
    private Musume musume;

    public Kakaa(Musuko mk, Musume mm) {
        musuko = mk;
        musume = mm;
    }

    public String openLatest() {
        File file = musume.getLatest(directory);
        musuko.update(file);
        return read(file);
    }

    private String read(File file) {
        return null;
    }

    public void saveAndUpdate(String text) {
        save(text);
        musuko.update();
    }

    public void save(String text) {
        File file = musuko.getFile(directory);
    }
}
