package com.example.givegiftdesign.request;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DataModalApi {
    @POST("/generate_ideas")
    Call<DataModal> createPost(@Body DataModal dataModal);
}
