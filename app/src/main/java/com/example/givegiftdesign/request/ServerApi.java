package com.example.givegiftdesign.request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author dan
 * getFrieds потом поменяет тип. Нужно получать данные в соответствующий объект
 */
public interface ServerApi {
    @GET("account")
    Call<Account> getAccount();

    @GET("getFriends")
    List<String> getFriends();

    @POST("setFriends")
    void setFriends();
}
