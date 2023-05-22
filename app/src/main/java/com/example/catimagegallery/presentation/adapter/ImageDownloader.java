package com.example.catimagegallery.presentation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.catimagegallery.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDownloader {
    public static void downloadImage(Context context, String imageUrl) {

            Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            saveImageToExternalStorage(resource, context, imageUrl);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

    }
    private static void saveImageToExternalStorage(Bitmap imageBitmap, Context context, String imageUrl) {
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CatImages");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileExtension = getFileExtensionFromUrl(imageUrl);
        String fileName = "cat_image_" + System.currentTimeMillis() + fileExtension;
        File file = new File(directory, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);

            if (fileExtension.equalsIgnoreCase(".gif")) {
                Glide.with(context)
                        .asFile()
                        .load(imageUrl)
                        .into(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                try {
                                    FileInputStream fis = new FileInputStream(resource);
                                    byte[] buffer = new byte[1024];
                                    int length;
                                    while ((length = fis.read(buffer)) != -1) {
                                        fos.write(buffer, 0, length);
                                    }
                                    fis.close();
                                    fos.flush();
                                    fos.close();
                                    MediaScannerConnection.scanFile(context,
                                            new String[]{file.getAbsolutePath()},
                                            new String[]{"image/gif"},
                                            null);
                                    Toast.makeText(context, R.string.imagen_descargada, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, R.string.imagen_error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                MediaScannerConnection.scanFile(context,
                        new String[]{file.getAbsolutePath()},
                        new String[]{"image/jpeg"},
                        null);
                Toast.makeText(context, R.string.imagen_descargada, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.imagen_error, Toast.LENGTH_SHORT).show();
        }
    }

    private static String getFileExtensionFromUrl(String url) {
        String extension = "";
        int lastDotIndex = url.lastIndexOf(".");
        if (lastDotIndex != -1) {
            extension = url.substring(lastDotIndex);
        }
        return extension;
    }

}
