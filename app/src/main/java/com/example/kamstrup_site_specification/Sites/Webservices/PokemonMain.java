package com.example.kamstrup_site_specification.Sites.Webservices;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.kamstrup_site_specification.R;
/*
 * @Author Kasper Knop - https://github.com/KasperKnop/NetworkingExample
 * */

public class PokemonMain extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    PokemonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemons);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);

        viewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        viewModel.getPokemon().observe(this, new Observer<Pokemon>() {
            @Override
            public void onChanged(Pokemon pokemon) {
                Glide.with(PokemonMain.this).load(pokemon.getImageUrl()).into(imageView);
            }
        });
    }

    public void updatePokemon(View view) {
        viewModel.updatePokemon(editText.getText().toString());
    }
}
