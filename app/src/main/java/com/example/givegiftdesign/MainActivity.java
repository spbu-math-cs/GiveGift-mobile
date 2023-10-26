package com.example.givegiftdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private ImageView giftImageView;
    private TextView giftDescription;
    private Button giftSearch;

    // Тестовый объект
    private GiftBlock giftBlock() {
        return new GiftBlock(
                "https://i.imgur.com/67tSocD.jpeg",
                "Попуг",
                "https://imgur.com/gallery/67tSocD"
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        giftImageView = findViewById(R.id.gift_image);
        giftDescription = findViewById(R.id.gift_desc);
        giftSearch = findViewById(R.id.gift_search);

        displayGiftBlockInfo(giftBlock());
    }

    private void displayGiftBlockInfo(GiftBlock gb) {
        // Временный код
        // Показывает как получить картинку из инета и заменить её в activity_main
        Picasso.get().load(gb.getImageUrl()).into(giftImageView);
        giftDescription.setText(gb.getDescription());

        // Логика кнопки может находиться в другом месте
    }
}