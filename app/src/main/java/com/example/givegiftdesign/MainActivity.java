package com.example.givegiftdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.view.Menu;
import android.widget.ImageButton;

import com.example.givegiftdesign.giftidea.GiftBlock;
import com.example.givegiftdesign.giftidea.NewGiftConstructor;
import com.example.givegiftdesign.preference.PreferenceActivity;
import com.example.givegiftdesign.request.Idea;
import com.example.givegiftdesign.request.IdeaClient;
import com.example.givegiftdesign.request.Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "1";
    /**
     * При запуске приложения запрашивается инфа с веба и инициализиует все необходимые поля
     * например 'предпочтения', 'аккаунт' и тп
     */
    private Request request;

    private ArrayList<GiftBlock> giftBlocks;

    /**
     * В этот слой добавляются сгенерированные идеи для подарка
     */
    LinearLayoutCompat mainLayout;

    // Люблю попугов

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Точка входа в приложение
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Уведомления
        createNotificationChannel();

        Intent intent = new Intent(MainActivity.this, Reminder.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtSys = System.currentTimeMillis();
        long twoSecs = 5 * 1000;

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                timeAtSys + twoSecs,
                pendingIntent);
        //

//        giftBlocks = new ArrayList<>();
//        giftBlocks.add(new GiftBlock(
//                "https://basket-10.wb.ru/vol1508/part150806/150806145/images/big/1.jpg",
//                "Фонарик",
//                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%A4%D0%BE%D0%BD%D0%B0%D1%80%D0%B8%D0%BA&priceU=000%3B3600000#c150806145"
//        ));
//        giftBlocks.add(new GiftBlock(
//                "https://basket-05.wb.ru/vol835/part83504/83504705/images/big/1.jpg",
//                "Книга",
//                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%9A%D0%BD%D0%B8%D0%B3%D0%B0&priceU=000%3B3600000"
//        ));
//        giftBlocks.add(new GiftBlock(
//                "https://basket-02.wb.ru/vol179/part17998/17998675/images/big/1.jpg",
//                "Плакат",
//                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%9F%D0%BB%D0%B0%D0%BA%D0%B0%D1%82&priceU=000%3B3600000"
//        ));
//        giftBlocks.add(new GiftBlock(
//                "https://basket-12.wb.ru/vol1837/part183731/183731287/images/big/1.jpg",
//                "Альбом",
//                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%90%D0%BB%D1%8C%D0%B1%D0%BE%D0%BC&priceU=000%3B3600000"
//        ));
//        giftBlocks.add(new GiftBlock(
//                "https://basket-10.wb.ru/vol1404/part140484/140484930/images/big/1.jpg",
//                "Компас",
//                "https://www.wildberries.ru/catalog/0/search.aspx?page=1&sort=popular&search=%D0%9A%D0%BE%D0%BC%D0%BF%D0%B0%D1%81&priceU=000%3B3600000"
//        ));

        // Тут производится запрос
        /**
         * При запуске приложения запрашивается инфа с веба и инициализиует все необходимые поля
         * например 'предпочтения', 'аккаунт' и тп
         */
//        request = new Request();
//        request.req();
        //

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.baseline_account_circle_24));
        setSupportActionBar(toolbar);

        mainLayout = findViewById(R.id.gift_layout);

        // Ципа dancing
        ImageButton mascot = findViewById(R.id.maskot);
        final boolean[] isChanged = {false};
        mascot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && isChanged[0]){
                    mascot.setImageResource(R.mipmap.greetings_foreground);
                    isChanged[0] = false;
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    mascot.setImageResource(R.mipmap.gift_foreground);
                    isChanged[0] = true;
                }
                return false;
            }
        });

        // Кнопка для генерации идей на основе предпочтений
        Button giftIdeaBtn = findViewById(R.id.gift_idea);
        giftIdeaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mascot.setImageResource(R.mipmap.gift_foreground);

//                for (GiftBlock gb : giftBlocks) {
//
//                    NewGiftConstructor newGiftConstructor = new NewGiftConstructor(
//                            getLayoutInflater().inflate(R.layout.activity_main_gift, mainLayout, false),
//                            MainActivity.this
//                    );
//
//                    View giftIdeaView = newGiftConstructor.giftViewParams(gb);
//                    mainLayout.addView(giftIdeaView);
//
//                }

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .build();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://faq-givegift-req.ru/")
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();

                IdeaClient client = retrofit.create(IdeaClient.class);
                Call<List<Idea>> call = client.ideasForUser();

                call.enqueue(new Callback<List<Idea>>() {
                    @Override
                    public void onResponse(Call<List<Idea>> call, Response<List<Idea>> response) {
                        if (response.isSuccessful()) {
                            ViewGroup parent = (ViewGroup) mascot.getParent();
                            parent.removeView(mascot);

                            Log.d("Callback", "response: " + response.body().size());
                            Log.d("Callback body", "response: " + response.body().get(0));

                            List<Idea> ideas = response.body();

                            for (Idea idea : ideas) {

                                GiftBlock gb = new GiftBlock(idea.getImg_link(),
                                        idea.getTitle(),
                                        idea.getMarket_link());

                                NewGiftConstructor newGiftConstructor = new NewGiftConstructor(
                                        getLayoutInflater().inflate(R.layout.activity_main_gift, mainLayout, false),
                                        MainActivity.this
                                );

                                View giftIdeaView = newGiftConstructor.giftViewParams(gb);
                                mainLayout.addView(giftIdeaView);

                            }
                        }
                        else
                            Log.d("Response code", "response code " + response.code());
                    }

                    @Override
                    public void onFailure(Call<List<Idea>> call, Throwable t) {
                        Log.w("Failure", "failure: " + t);
                    }
                });
            }
        });
        //

        // Кнопка для перехода в activity, где происходит выбор предпочтений и цены
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, PreferenceActivity.class)));

        // cWcinszDWy-weZftXcde5r:APA91bFDM0auwB-JRL0d2Nbf7EVYqczI-PlzfXlmTm-31dr_yopZQglKr1tz7jEiTx5stC7a-stsizIsN2rGubJeo9QCVwpi8L3ClZ3si-7nyJPHwKXFqs73dwTLHIfMTqoQyzRgoMeo
        // Получаем токен устрйства для возможности отправки попапов
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
////                        String msg = token;
//                        Log.d(TAG, msg);
////                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
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
                // startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case 2:
                Log.d("Item friends", "clicked");
                startActivity(new Intent(this, FriendsActivity.class));
                return true;
            case 3:
                Log.d("Exit", "clicked");
                startActivity(new Intent(this, LoginActivity.class));
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