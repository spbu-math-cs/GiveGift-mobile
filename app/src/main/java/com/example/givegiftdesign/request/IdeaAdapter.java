package com.example.givegiftdesign.request;


import com.example.givegiftdesign.giftidea.GiftBlock;

import java.util.List;

public class IdeaAdapter {
    private List<Idea> values;

    public IdeaAdapter(List<Idea> values) {
        this.values = values;
    }

    public GiftBlock getGift(int pos) {
        Idea item = values.get(pos);
        return null;
    }
}
