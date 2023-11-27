package com.example.givegiftdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.Menu;

import com.example.givegiftdesign.request.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    /**
     * При запуске приложения запрашивается инфа с веба и инициализиует все необходимые поля
     * например 'предпочтения', 'аккаунт' и тп
     */
    private Request request;

    // Тестовый объект
    private GiftBlock giftBlock() {
        return new GiftBlock(
                "https://i.imgur.com/67tSocD.jpeg",
                "",
                "https://imgur.com/gallery/67tSocD"
        );
    }

    /**
     * В этот слой добавляются сгенерированные идеи для подарка
     */
    LinearLayoutCompat mainLayout;

    // Люблю попугов

    /**
     * Точка входа в приложение
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Тут производится запрос
        request = new Request();
        request.req();
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainLayout = findViewById(R.id.gift_layout);

        // Кнопка для генерации идей на основе предпочтений
        Button giftIdeaBtn = findViewById(R.id.gift_idea);
        giftIdeaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftBlockConstructor giftBlockConstructor = new GiftBlockConstructor(
                        new CardView(MainActivity.this),
                        new LinearLayout(MainActivity.this),
                        new ImageView(MainActivity.this),
                        new TextView(MainActivity.this),
                        new Button(MainActivity.this),
                        getResources()
                );

                View giftIdeaView = giftBlockConstructor.giftViewParams(giftBlock());
                mainLayout.addView(giftIdeaView);
            }
        });
        //

//        giftImageView = findViewById(R.id.gift_image_one);
//        giftDescription = findViewById(R.id.gift_desc_one);
//        giftSearch = findViewById(R.id.gift_search_one);
//
//        displayGiftBlockInfo(giftBlock());
//
//        giftImageView = findViewById(R.id.gift_image_two);
//        giftDescription = findViewById(R.id.gift_desc_two);
//        giftSearch = findViewById(R.id.gift_search_two);
//
//        displayGiftBlockInfo(giftBlock());

        // Кнопка для перехода в activity, где происходит выбор предпочтений и цены
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, PreferenceActivity.class)));
    }

    /**
     * Скорее всего уже не нужно
     * Данный код был перенесен в GiftBlockConstructor
     * @param gb - ignored
     */
    @Deprecated
    private void displayGiftBlockInfo(GiftBlock gb) {
        // Временный код
        // Показывает как получить картинку из инета и заменить её в activity_main
//        Picasso.get().load(gb.getImageUrl()).into(giftImageView);
//        giftDescription.setText(gb.getDescription());

        // Логика кнопки может находиться в другом месте
    }

    /**
     * Меню с возможностью перехода по activity профиль, друзья и тп
     * @param item The menu item that was selected.
     *
     * @return - true/false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getOrder()) {
            case 1:
                Log.d("Item profile", "clicked");
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case 2:
                Log.d("Item friends", "clicked");
                startActivity(new Intent(this, FriendsActivity.class));
                return true;
            case 3:
                Log.d("Exit", "clicked");
                startActivity(new Intent(this, RegistryActivity.class));
                return true;
        }

        return false;
    }

    /**
     * Просто создается меню для доступа к профилю, друзьям и выходу
     * @param menu The options menu in which you place your items.
     *
     * @return - true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}