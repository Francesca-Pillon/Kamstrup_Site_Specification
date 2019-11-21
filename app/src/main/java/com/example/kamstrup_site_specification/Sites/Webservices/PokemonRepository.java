package com.example.kamstrup_site_specification.Sites.Webservices;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * @Author Kasper Knop - https://github.com/KasperKnop/NetworkingExample
 * */

public class PokemonRepository {

    private static PokemonRepository instance;
    private MutableLiveData<Pokemon> pokemon;

    private PokemonRepository() {
        pokemon = new MutableLiveData<>();
    }

    public static synchronized PokemonRepository getInstance() {
        if (instance == null) {
            instance = new PokemonRepository();
        }
        return instance;
    }

    public LiveData<Pokemon> getPokemon() {
        return pokemon;
    }

    public void updatePokemon(String pokemonName) {
        PokemonApi pokemonApi = ServiceGenerator.getPokemonApi();
        Call<PokemonResponse> call = pokemonApi.getPokemon(pokemonName);
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.code() == 200) {
                    pokemon.setValue(response.body().getPokemon());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {

            }
        });
    }
}
