package com.example.givegiftdesign.request;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Request {
    public void req() {
        // Работающий запрос на сторонний сервер
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rawgit.com/startandroid/data/master/messages/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PreferenceApi prefApi = retrofit.create(PreferenceApi.class);
//        TestApi testApi = retrofit.create(TestApi.class);

        Call<List<Preference>> prefs = prefApi.prefs();
//        Call<List<Test>> acc = testApi.data();

        prefs.enqueue(new Callback<List<Preference>>() {
            @Override
            public void onResponse(Call<List<Preference>> call, Response<List<Preference>> response) {
                if (response.isSuccessful()) {
                    Log.d("Callback", "response: " + response.body().size());
                    Log.d("Callback body", "response: " + response.body().get(0));
                }
                else
                    Log.d("Response code", "response code " + response.code());
            }

            @Override
            public void onFailure(Call<List<Preference>> call, Throwable t) {
                Log.w("Failure", "failure: " + t);
            }
        });

//        acc.enqueue(new Callback<List<Test>>() {
//            @Override
//            public void onResponse(Call<List<Test>> call, Response<List<Test>> response) {
//                if (response.isSuccessful())
//                    Log.d("Callback", "response: " + response.body());
//                else
//                    Log.d("Response code", "response code " + response.code());
//            }
//
//            @Override
//            public void onFailure(Call<List<Test>> call, Throwable t) {
//                Log.w("Failure", "failure: " + t);
//            }
//        });

        // Временная заглушка на предпочтения
        Account.tempInterests();

    }
}
