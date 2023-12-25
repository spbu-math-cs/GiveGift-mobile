package com.example.givegiftdesign.request;

import java.util.ArrayList;
import java.util.List;

public class RequestData {
    List<String> tags = new ArrayList<>();
    List<Integer> price_range = new ArrayList<>();

    public RequestData(List<String> tags, List<Integer> price_range) {
        this.tags = tags;
        this.price_range = price_range;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Integer> getPrice_range() {
        return price_range;
    }

    public void setPrice_range(List<Integer> price_range) {
        this.price_range = price_range;
    }
}
