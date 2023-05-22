package com.example.catimagegallery.presentation.presenter;



import androidx.annotation.NonNull;

import com.example.catimagegallery.data.api.CatApiClient;
import com.example.catimagegallery.data.model.CatBreed;
import com.example.catimagegallery.data.model.CatImage;
import com.example.catimagegallery.presentation.view.CatView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatPresenter {
    private CatView view;
    private CatApiClient apiClient;

    public CatPresenter(CatView view) {
        this.view = view;
        this.apiClient = new CatApiClient();
    }

    public void loadCatImages() {
        apiClient.getCats(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatImage>> call, @NonNull Response<List<CatImage>> response) {
                if (response.isSuccessful()) {
                    List<CatImage> catImages = response.body();
                    view.showCatImages(catImages);
                } else {
                    view.showError("Error al cargar las imágenes de gatos");
                }
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                view.showError("Error de conexión");
            }
        });
    }

    public void loadCatBreeds() {
        apiClient.getCatBreeds(new Callback<List<CatBreed>>() {
            @Override
            public void onResponse(Call<List<CatBreed>> call, Response<List<CatBreed>> response) {
                if (response.isSuccessful()) {
                    List<CatBreed> catBreeds = response.body();
                    view.showCatBreeds(catBreeds);
                } else {
                    view.showError("Failed to load cat breeds");
                }
            }

            @Override
            public void onFailure(Call<List<CatBreed>> call, Throwable t) {
                view.showError("Failed to load cat breeds: " + t.getMessage());
            }
        });
    }

    public void searchCats(String query, int limit) {
        apiClient.getSearchCats(query, limit, new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if (response.isSuccessful()) {
                    List<CatImage> catImages = response.body();
                    view.showCatImages(catImages);
                } else {
                    view.showError("Error al cargar las imágenes de gatos");
                }
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                view.showError("Error de conexión");
            }
        });
    }

}
