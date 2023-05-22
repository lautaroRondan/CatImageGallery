package com.example.catimagegallery.presentation.view;

import android.widget.ArrayAdapter;

import com.example.catimagegallery.data.model.CatBreed;
import com.example.catimagegallery.data.model.CatImage;

import java.util.List;

public interface CatView {
    void showCatImages(List<CatImage> catImages);
    void showError(String errorMessage);
    void showCatBreeds(List<CatBreed> breeds);
}
