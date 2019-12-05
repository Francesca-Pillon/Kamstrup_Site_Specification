package com.example.kamstrup_site_specification.Sites.QuoteWebservice;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteAPI {

    @GET("v1/quotes/random")
    Call<TrumpQuote> getPosts();


}
