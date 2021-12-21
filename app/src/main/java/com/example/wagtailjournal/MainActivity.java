package com.example.wagtailjournal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
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
                                (Musuko)co.geti("musuko"));
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

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void onClick(View view) {
        TextView text = (TextView)findViewById(R.id.editText);
        getOyaji().requestPermission(text.getText().toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView text = (TextView)findViewById(R.id.editText);
        getOyaji().receivePermission(text.getText().toString(), resultCode);
    }
}
