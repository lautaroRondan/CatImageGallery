package com.example.catimagegallery.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.catimagegallery.data.model.CatBreed;

import java.util.List;

public class CatBreedAdapter extends ArrayAdapter<CatBreed> {
    private LayoutInflater inflater;
    private List<CatBreed> catBreeds;

    public CatBreedAdapter(Context context, List<CatBreed> catBreeds) {
        super(context, 0, catBreeds);
        inflater = LayoutInflater.from(context);
        this.catBreeds = catBreeds;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CatBreed breed = catBreeds.get(position);
        holder.nameTextView.setText(breed.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    private static class ViewHolder {
        TextView nameTextView;
    }
}

