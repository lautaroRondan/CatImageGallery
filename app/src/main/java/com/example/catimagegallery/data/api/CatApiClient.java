package com.example.catimagegallery.data.api;

import com.example.catimagegallery.data.model.CatBreed;
import com.example.catimagegallery.data.model.CatImage;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatApiClient {
    private static final String BASE_URL = "https://api.thecatapi.com/v1/";
    private static final String API_KEY = "live_h6WWx38FQA02V7pOCHl17QRtltaCra0TVmLqgg73H4URTCgW0oCbiKWw8TIyfhKP";
    private CatApiService catApiService;

    public CatApiClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request requestWithApiKey = originalRequest.newBuilder()
                        .header("x-api-key", API_KEY)
                        .build();
                return chain.proceed(requestWithApiKey);
            }
        });

        OkHttpClient okHttpClient = httpClientBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        catApiService = retrofit.create(CatApiService.class);
    }

    public void getCats(Callback<List<CatImage>> callback) {
        Call<List<CatImage>> call = catApiService.getCats();
        call.enqueue(callback);
    }

    public void getSearchCats(String keywords, int i, Callback<List<CatImage>> callback) {
        Call<List<CatImage>> call = catApiService.getSearchCats(keywords, i);
        call.enqueue(callback);
    }

    public void getCatBreeds(Callback<List<CatBreed>> callback) {
        Call<List<CatBreed>> call = catApiService.getCatBreeds();
        call.enqueue(callback);
    }

}
