package com.example.catimagegallery.data.api;

import com.example.catimagegallery.data.model.CatBreed;
import com.example.catimagegallery.data.model.CatImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatApiService {
    @GET("images/search?limit=10")
    Call<List<CatImage>> getCats();

    @GET("images/search")
    Call<List<CatImage>> getSearchCats(@Query("breed_ids") String breedIds, @Query("limit") int limit);

    @GET("breeds")
    Call<List<CatBreed>> getCatBreeds();

}
