package com.example.wagtailjournal;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Musuko {
    Date timestamp;

    public File getFile() {
        if (timestamp == null)
            updateTimestamp();
        SimpleDateFormat sdf = new SimpleDateFormat("/storage/emulated/0/Documents/notes/yyyyMMddHHmmss.txt");
        String filename = sdf.format(timestamp);
        return new File(filename);
    }

    public void updateTimestamp() {
        timestamp = new Date();
    }
}
