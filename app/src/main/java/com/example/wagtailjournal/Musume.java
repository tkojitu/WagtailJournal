package com.example.wagtailjournal;

import java.io.File;

public class Musume {
    public File searchLatestJournal(File directory) {
        File found = null;
        if (!directory.exists())
            return null;
        for (File file : directory.listFiles()) {
            if (found == null) {
                found = file;
                continue;
            }
            if (file.lastModified() > found.lastModified()) {
                found = file;
            }
        }
        return found;
    }
}
