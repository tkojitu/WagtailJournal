package com.github.tkojitu.wagtailjournal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Container container = new Container();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setupContainer();
        getOyaji().startup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        getOyaji().saveJournal(getEditText().getText().toString());
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
        return (EditText)findViewById(R.id.edit_text);
    }

    public void onClickNew(MenuItem item) {
        getOyaji().newJournal(getEditText().getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getOyaji().receivePermission(requestCode, resultCode);
    }

    public void clear() {
        getEditText().getText().clear();
    }

    public void update(String text) {
        getEditText().setText(text);
        getEditText().setSelection(getEditText().getText().length());
    }
}
