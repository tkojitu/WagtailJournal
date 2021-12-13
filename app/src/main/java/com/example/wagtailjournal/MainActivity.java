package com.example.wagtailjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private class OyajiFactory implements ServiceFactory {
        @Override
        public Object create(Container co) {
            return new Oyaji((Kakaa)co.geti("kakaa"));
        }
    }

    private class KakaaFactory implements ServiceFactory {
        @Override
        public Object create(Container co) {
            return new Kakaa((Musuko)co.geti("musuko"));
        }
    }

    private class MusukoFactory implements ServiceFactory {
        @Override
        public Object create(Container co) {
            return new Musuko();
        }
    }

    private Container container = new Container();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupContainer();
    }

    private void setupContainer() {
        container.defserv("oyaji", new OyajiFactory())
                .defserv("kakaa", new KakaaFactory())
                .defserv("musuko", new MusukoFactory());
    }
}
