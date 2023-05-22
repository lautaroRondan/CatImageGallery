package com.example.catimagegallery.presentation.view;

import static com.example.catimagegallery.presentation.adapter.ImageDownloader.downloadImage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.catimagegallery.R;


public class FullScreenImageActivity extends AppCompatActivity {
    private ImageView imageViewFullScreen;
    private Button buttonDownload;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        imageViewFullScreen = findViewById(R.id.imageViewFullScreen);
        buttonDownload = findViewById(R.id.buttonDownload);
        imageUrl = getIntent().getStringExtra("image_url");

        Glide.with(this)
                .load(imageUrl)
                .into(imageViewFullScreen);

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWriteStoragePermission();
            }
        });
    }

    private void checkWriteStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                downloadImage(this, imageUrl);
                checkWriteStoragePermission();
            }
        } else {
            downloadImage(this, imageUrl);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadImage(this, imageUrl);
            } else {
                Toast.makeText(this, R.string.almacenamiento_denegado, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
