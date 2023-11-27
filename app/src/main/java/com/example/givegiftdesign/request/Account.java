package com.example.givegiftdesign.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    private String id;
    private String nickname;
    private String email;
    private String about;
    private String birth_date;
    private static List<String> interests;

    public static void tempInterests() {
        interests = new ArrayList<>();

        interests.add("Кино");
        interests.add("Мультфильмы");
        interests.add("Походы");
    }

    public static void updateInterests(List<String> inter) {
        interests = inter;
    }

    public static List<String> getInterests() {
        return interests;
    }
}
