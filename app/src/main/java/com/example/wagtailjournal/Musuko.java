package com.example.wagtailjournal;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Musuko {
    Date timestamp;

    public File getFile(File dir) {
        if (timestamp == null)
            updateTimestampByFile();
        String filename = getDateFormat().format(timestamp);
        return new File(dir, filename);
    }

    private SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmss'.txt'");
    }

    public void updateTimestampByFile() {
        timestamp = new Date();
    }

    public void updateTimestampByFile(File file) throws ParseException {
        if (file == null) {
            updateTimestampByFile();
            return;
        }
        String filename = file.getName();
        timestamp = parseDate(filename);
    }

    private Date parseDate(String date) throws ParseException {
        try {
            return getDateFormat().parse(date);
        } catch (ParseException e) {
            return new SimpleDateFormat("yyyy-MM-dd'.txt'").parse(date);
        }
    }
}
