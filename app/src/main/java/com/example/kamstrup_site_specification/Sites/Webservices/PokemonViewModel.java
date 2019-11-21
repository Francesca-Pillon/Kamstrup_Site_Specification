package com.example.kamstrup_site_specification.Sites.Webservices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/*
 * @Author Kasper Knop - https://github.com/KasperKnop/NetworkingExample
 * */

public class PokemonViewModel extends ViewModel {

    PokemonRepository repository;

    public PokemonViewModel(){
        repository = PokemonRepository.getInstance();
    }

    public LiveData<Pokemon> getPokemon() {
        return repository.getPokemon();
    }

    public void updatePokemon(String s) {
        repository.updatePokemon(s);
    }
}
