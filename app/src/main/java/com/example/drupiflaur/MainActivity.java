package com.example.drupiflaur;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.raodevs.touchdraw.TouchDrawView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TouchDrawView touchDrawView = findViewById(R.id.canvas);
        touchDrawView.setPaintColor(Color.BLACK);


        Button clear = findViewById(R.id.clearButton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                touchDrawView.clear();
            }
        });

        Button generate = findViewById(R.id.generateButton);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText author = findViewById(R.id.authorText);
                EditText poem = findViewById(R.id.poemText);

                Intent intent = new Intent(MainActivity.this.getApplicationContext(), GeneratedActivity.class);
                intent.putExtra("authorText", author.getText().toString());
                intent.putExtra("poemText", poem.getText().toString());
                try {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        String path = Environment.getExternalStorageDirectory().toString();
                        OutputStream fOut = null;
                        File file = new File(path, "image.PNG");
                        fOut = new FileOutputStream(file);
                        Bitmap bitmap = touchDrawView.getFile();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        intent.putExtra("imagePath", file.getAbsolutePath());
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainActivity.this.startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
