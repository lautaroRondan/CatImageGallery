package com.example.catimagegallery.presentation.view;

import static com.example.catimagegallery.presentation.adapter.ImageDownloader.downloadImage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.catimagegallery.R;

public class DialogUtils {
    private static AlertDialog dialog;

    public static void showOptionsDialog(Context context, final String imageUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_options, null);
        builder.setView(dialogView);

        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
        Button downloadButton = dialogView.findViewById(R.id.button_download);
        Button viewPhotoButton = dialogView.findViewById(R.id.button_view_photo);

        titleTextView.setText("¿Que deseas hacer?");

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage(context, imageUrl);
                dismissDialog();
            }
        });

        viewPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenImageActivity.class);
                intent.putExtra("image_url", imageUrl);
                context.startActivity(intent);
                dismissDialog();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
