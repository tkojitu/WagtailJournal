package com.example.wagtailjournal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Kakaa {
    public Kakaa() {
    }

    public void save(File file, String text) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(text);
        }
    }
}
