package com.example.drupiflaur;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raodevs.touchdraw.TouchDrawView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GeneratedActivity extends AppCompatActivity {

    private static int COUNT = 0;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generated_main);

        String authorText = getIntent().getStringExtra("authorText").toLowerCase();
        String poemText = getIntent().getStringExtra("poemText").toLowerCase();

        TextView author = findViewById(R.id.authorText);
        TextView poem = findViewById(R.id.poemText);

        author.setText("-" + authorText);
        poem.setText(poemText);

        ImageView imageView = findViewById(R.id.imageView);

        if (ContextCompat.checkSelfPermission(GeneratedActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            String path = getIntent().getStringExtra("imagePath");
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse("file://" + path));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(GeneratedActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }


        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                GeneratedActivity.this.startActivity(new Intent(GeneratedActivity.this.getApplicationContext(), MainActivity.class));
            }
        });
    }
}
