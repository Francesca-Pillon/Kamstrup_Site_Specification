package com.example.kamstrup_site_specification.Sites.Webservices;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
 * @Author Kasper Knop - https://github.com/KasperKnop/NetworkingExample
 * */

public interface PokemonApi {

    @GET("api/v2/pokemon/{name}")
    Call<PokemonResponse> getPokemon(@Path("name") String name);
}