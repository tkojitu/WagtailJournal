package com.github.tkojitu.wagtailjournal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

public class MainActivity extends AppCompatActivity {

    private Container container = new Container();

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() != Activity.RESULT_OK) {
                        Toast.makeText(MainActivity.this, "permission denied", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent resultData = result.getData();
                    if (resultData == null) {
                        Toast.makeText(MainActivity.this, "permission denied", Toast.LENGTH_LONG).show();
                        return;
                    }
                    getOyaji().loadJournal();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setupContainer();
        requestPermission();
    }

    private void requestPermission() {
        if (Environment.isExternalStorageManager()) {
            getOyaji().loadJournal();
            return;
        }
        Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
        Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
        launcher.launch(intent);
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

    public void clear() {
        getEditText().getText().clear();
    }

    public void update(String text) {
        getEditText().setText(text);
        getEditText().setSelection(getEditText().getText().length());
    }
}
