package com.example.catimagegallery.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catimagegallery.R;
import com.example.catimagegallery.data.model.CatBreed;
import com.example.catimagegallery.data.model.CatImage;
import com.example.catimagegallery.presentation.adapter.CatAdapter;
import com.example.catimagegallery.presentation.adapter.CatBreedAdapter;
import com.example.catimagegallery.presentation.presenter.CatPresenter;


import java.util.List;
public  class CatActivity extends AppCompatActivity implements CatView{

    private RecyclerView recyclerViewCats;
    private CatAdapter catAdapter;
    private List<CatImage> catImages;
    private CatPresenter catPresenter;
    private Spinner spinnerBreed;
//    private List<String> catBreeds;
    private String selectedBreedId;
    private TextView textNoImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        recyclerViewCats = findViewById(R.id.recyclerViewCats);
        recyclerViewCats.setLayoutManager(new LinearLayoutManager(this));
        catAdapter = new CatAdapter(this);
        recyclerViewCats.setAdapter(catAdapter);
        catPresenter = new CatPresenter(this);

        Button buttonSearch = findViewById(R.id.buttonSearch);
        textNoImages = findViewById(R.id.textNoImages);
        textNoImages.setVisibility(View.GONE);

        spinnerBreed = findViewById(R.id.spinnerBreed);
        catPresenter.loadCatBreeds();

        spinnerBreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedBreedName = parent.getItemAtPosition(position).toString();
                CatBreed selectedBreed = (CatBreed) spinnerBreed.getSelectedItem();
                selectedBreedId = selectedBreed.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Implementa el manejo si no se selecciona ninguna opción
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = selectedBreedId;
                int limit = 10;
                catPresenter.searchCats(searchQuery, limit);
            }
        });

        catPresenter.loadCatImages();
    }

    @Override
    public void showCatImages(List<CatImage> catImages) {
        if (catImages.isEmpty()) {
            recyclerViewCats.setVisibility(View.GONE);
            textNoImages.setVisibility(View.VISIBLE);
        } else {
            recyclerViewCats.setVisibility(View.VISIBLE);
            textNoImages.setVisibility(View.GONE);
            this.catImages = catImages;
            catAdapter.setImages(this.catImages);
        }
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCatBreeds(List<CatBreed> breeds) {
        CatBreedAdapter adapter = new CatBreedAdapter(this, breeds);
        spinnerBreed.setAdapter(adapter);
    }
}
