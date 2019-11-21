package com.example.kamstrup_site_specification.Sites.Webservices;

/*
 * @Author Kasper Knop - https://github.com/KasperKnop/NetworkingExample
 * */

public class PokemonResponse {
    private int id;
    private String name;
    private Sprites sprites;

    public Pokemon getPokemon(){
        return new Pokemon(id,name,sprites.front_default);
    }

    private class Sprites {
        private String front_default;
    }
}
