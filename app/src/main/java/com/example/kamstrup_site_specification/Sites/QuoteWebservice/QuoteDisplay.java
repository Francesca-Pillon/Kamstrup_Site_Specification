package com.example.kamstrup_site_specification.Sites.QuoteWebservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kamstrup_site_specification.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteDisplay extends AppCompatActivity {
    private TextView textViewresult;
    private Button buttonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote_layout);

        textViewresult = findViewById(R.id.text_view_result);
        buttonResult = findViewById(R.id.button_result);
        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.whatdoestrumpthink.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();




                QuoteAPI quoteAPI = retrofit.create(QuoteAPI.class);
                Call<TrumpQuote> call = quoteAPI.getPosts();
                call.enqueue(new Callback<TrumpQuote>() {
                    @Override
                    public void onResponse(Call<TrumpQuote> call, Response<TrumpQuote> response) {
                        if (!response.isSuccessful()) {
                            textViewresult.setText("code" + response.code());
                            return;
                        }
                        TrumpQuote posts = response.body();

                        textViewresult.setText(posts.getMessage());


                    }

                    @Override
                    public void onFailure(Call<TrumpQuote> call, Throwable t) {
                        textViewresult.setText(t.getMessage());
                    }
                });

            }
        });






    }
}
