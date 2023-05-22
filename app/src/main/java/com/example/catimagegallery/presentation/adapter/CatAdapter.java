package com.example.catimagegallery.presentation.adapter;

import static com.example.catimagegallery.presentation.adapter.ImageDownloader.downloadImage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.catimagegallery.R;
import com.example.catimagegallery.data.model.CatImage;
import com.example.catimagegallery.presentation.view.DialogUtils;
import com.example.catimagegallery.presentation.view.FullScreenImageActivity;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
    private List<String> imageUrls;
    private List<CatImage> catImages;
    private Context context;

    public CatAdapter(Context context) {
        this.context = context;
    }

    public void setImages(List<CatImage> catImages) {
        this.catImages = catImages;

        imageUrls = new ArrayList<>();
        for (CatImage catImage : catImages) {
            imageUrls.add(catImage.getUrl());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imagePosition = position * 2;

        if (imagePosition < imageUrls.size()) {
            String imageUrl1 = imageUrls.get(imagePosition);
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl1)
                    .into(holder.imageView1);
        }

        if (imagePosition + 1 < imageUrls.size()) {
            String imageUrl2 = imageUrls.get(imagePosition + 1);
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl2)
                    .into(holder.imageView2);
        } else {
            holder.imageView2.setVisibility(View.GONE);
        }

        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int firstImagePosition = imagePosition ;
                if (firstImagePosition < imageUrls.size()) {
                    String imageUrl = imageUrls.get(firstImagePosition);

                    Intent intent = new Intent(context, FullScreenImageActivity.class);
                    intent.putExtra("image_url", imageUrl);
                    context.startActivity(intent);
                }
            }
        });
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int secondImagePosition = imagePosition + 1;
                if (secondImagePosition < imageUrls.size()) {
                    String imageUrl = imageUrls.get(secondImagePosition);

                    Intent intent = new Intent(context, FullScreenImageActivity.class);
                    intent.putExtra("image_url", imageUrl);
                    context.startActivity(intent);
                }
            }
        });

        holder.imageView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int firstImagePosition = imagePosition ;
                if (firstImagePosition < imageUrls.size()) {
                    String imageUrl = imageUrls.get(firstImagePosition);
                    DialogUtils.showOptionsDialog(context, imageUrl);
                }
                return true;

            }
        });

        holder.imageView2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int secondImagePosition = imagePosition + 1;
                if (secondImagePosition < imageUrls.size()) {
                    String imageUrl = imageUrls.get(secondImagePosition);
                    DialogUtils.showOptionsDialog(context, imageUrl);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (imageUrls != null) ? (int) Math.ceil(imageUrls.size() / 2.0) : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1;
        ImageView imageView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.imageView1);
            imageView2 = itemView.findViewById(R.id.imageView2);
        }
    }

    private void showOptionsDialog(Context context, final String imageUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Opciones")
                .setMessage("Selecciona una opción")
                .setPositiveButton("Descargar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         downloadImage(context, imageUrl);
                    }
                })
                .setNegativeButton("Ver foto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, FullScreenImageActivity.class);
                        intent.putExtra("image_url", imageUrl);
                        context.startActivity(intent);;
                    }
                })
                .setNeutralButton("Cancelar", null)
                .show();
    }

}
