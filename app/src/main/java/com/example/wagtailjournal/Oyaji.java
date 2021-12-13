package com.example.wagtailjournal;

public class Oyaji {
    private Kakaa kakaa;

    public Oyaji(Kakaa ka) {
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
}
