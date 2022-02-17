package com.example.wagtailjournal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    static final int APP_STORAGE_ACCESS_REQUEST_CODE = 1;

    private Container container = new Container();

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupContainer();
        getOyaji().startup();
    }

    private void setupContainer() {
        MainActivity act = this;
        container.defserv("act",
                new ServiceFactory() {
                    @Override
                    public Object create(Container co) {
                        return act;
                    }
                })
        .defserv("oyaji",
                new ServiceFactory() {
                    @Override
                    public Object create(Container co) {
                        return new Oyaji((MainActivity)co.geti("act"),
                                (Kakaa)co.geti("kakaa"),
                                (Musuko)co.geti("musuko"),
                                (Musume)co.geti("musume"));
                    }
                })
        .defserv("kakaa",
                new ServiceFactory() {
                    @Override
                    public Object create(Container co) {
                        return new Kakaa();
                    }
                })
        .defserv("musuko",
                new ServiceFactory() {
                    @Override
                    public Object create(Container co) {
                        return new Musuko();
                    }
                })
        .defserv("musume",
                new ServiceFactory() {
                    @Override
                    public Object create(Container co) {
                        return new Musume();
                    }
                });
    }

    private Oyaji getOyaji() {
        return (Oyaji)container.geti("oyaji");
    }

    public EditText getEditText() {
        return (EditText)findViewById(R.id.editText);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void onClick(View view) {
        getOyaji().newJournal(getEditText().getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getOyaji().receivePermission(resultCode);
    }

    public void clear() {
        getEditText().getText().clear();
    }

    public void update(String text) {
        getEditText().setText(text);
    }
}
