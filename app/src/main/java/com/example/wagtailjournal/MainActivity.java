package com.example.wagtailjournal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

public class MainActivity extends AppCompatActivity {
    static final int APP_STORAGE_ACCESS_REQUEST_CODE = 1;

    private Container container = new Container();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupContainer();
    }

    private void setupContainer() {
        container.defserv("oyaji",
                new ServiceFactory() {
                    public Object create(Container co) {
                        return new Oyaji((Kakaa)co.geti("kakaa"));
                    }
                })
        .defserv("kakaa",
                new ServiceFactory() {
                    public Object create(Container co) {
                        return new Kakaa((Musuko)co.geti("musuko"), (Musume)co.geti("musume"));
                    }
                })
        .defserv("musuko",
                new ServiceFactory() {
                    public Object create(Container co) {
                        return new Musuko();
                    }
                })
        .defserv("musume",
                new ServiceFactory() {
                    public Object create(Container co) {
                        return new Musume();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void onClick(View view) {
        if (!Environment.isExternalStorageManager())  {
            requestPermission();
            return;
        }
    }

    private void requestPermission() {
        Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
        Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
        startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_STORAGE_ACCESS_REQUEST_CODE) {
            if (Environment.isExternalStorageManager())  {
                Toast.makeText(this, "permision granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "permision denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
