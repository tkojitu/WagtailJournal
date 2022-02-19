package com.example.wagtailjournal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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

    public String load(File file) throws IOException {
        if (file == null)
            return "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder buf = new StringBuilder();
            int ch;
            while ((ch = br.read()) >= 0) {
                buf.append((char)ch);
            }
            return buf.toString();
        }
    }
}
