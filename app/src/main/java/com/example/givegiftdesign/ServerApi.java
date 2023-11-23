package com.example.givegiftdesign;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author dan
 * getFrieds потом поменяет тип. Нужно получать данные в соответствующий объект
 */
public interface ServerApi {
    @GET("getPreferences")
    List<String> getPrefs();

    @GET("getFriends")
    List<String> getFriends();

    @POST("setPreferences")
    void setPrefs();

    @POST("setFriends")
    void setFriends();
}
