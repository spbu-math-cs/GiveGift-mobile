package com.example.givegiftdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.view.Menu;

import com.example.givegiftdesign.request.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

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
                "Попуг",
                "https://imgur.com/gallery/67tSocD"
        );
    }

    private ArrayList<GiftBlock> giftBlocks;

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

        giftBlocks = new ArrayList<>();
        giftBlocks.add(new GiftBlock(
                "https://basket-10.wb.ru/vol1508/part150806/150806145/images/big/1.jpg",
                "Фонарик",
                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%A4%D0%BE%D0%BD%D0%B0%D1%80%D0%B8%D0%BA&priceU=000%3B3600000#c150806145"
        ));
        giftBlocks.add(new GiftBlock(
                "https://basket-05.wb.ru/vol835/part83504/83504705/images/big/1.jpg",
                "Книга",
                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%9A%D0%BD%D0%B8%D0%B3%D0%B0&priceU=000%3B3600000"
        ));
        giftBlocks.add(new GiftBlock(
                "https://basket-02.wb.ru/vol179/part17998/17998675/images/big/1.jpg",
                "Плакат",
                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%9F%D0%BB%D0%B0%D0%BA%D0%B0%D1%82&priceU=000%3B3600000"
        ));
        giftBlocks.add(new GiftBlock(
                "https://basket-12.wb.ru/vol1837/part183731/183731287/images/big/1.jpg",
                "Альбом",
                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%90%D0%BB%D1%8C%D0%B1%D0%BE%D0%BC&priceU=000%3B3600000"
        ));
        giftBlocks.add(new GiftBlock(
                "https://basket-10.wb.ru/vol1404/part140484/140484930/images/big/1.jpg",
                "Компас",
                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%9A%D0%BE%D0%BC%D0%BF%D0%B0%D1%81&priceU=000%3B3600000"
        ));

        // Тут производится запрос
        request = new Request();
        request.req();
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.baseline_account_circle_24));
        setSupportActionBar(toolbar);

        mainLayout = findViewById(R.id.gift_layout);

        // Кнопка для генерации идей на основе предпочтений
        Button giftIdeaBtn = findViewById(R.id.gift_idea);
        giftIdeaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                for (GiftBlock gb : giftBlocks) {

                    NewGiftConstructor newGiftConstructor = new NewGiftConstructor(
                            getLayoutInflater().inflate(R.layout.activity_main_gift, mainLayout, false),
                            MainActivity.this
                    );

                    View giftIdeaView = newGiftConstructor.giftViewParams(gb);
                    mainLayout.addView(giftIdeaView);

                }
            }
        });
        //

        // Кнопка для перехода в activity, где происходит выбор предпочтений и цены
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, PreferenceActivity.class)));
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