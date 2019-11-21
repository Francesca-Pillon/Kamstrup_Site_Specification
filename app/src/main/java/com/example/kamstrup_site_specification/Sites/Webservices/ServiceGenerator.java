package com.example.kamstrup_site_specification.Sites.Webservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * @Author Kasper Knop - https://github.com/KasperKnop/NetworkingExample
 * */

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PokemonApi pokemonApi = retrofit.create(PokemonApi.class);

    public static PokemonApi getPokemonApi() {
        return pokemonApi;
    }
}
