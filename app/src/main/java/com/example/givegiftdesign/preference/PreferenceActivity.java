package com.example.givegiftdesign.preference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.givegiftdesign.R;
import com.example.givegiftdesign.preference.PreferenceBlock;
import com.example.givegiftdesign.preference.Price;
import com.example.givegiftdesign.request.Account;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PreferenceActivity extends AppCompatActivity {

    /**
     * Контейнер, куда помещаются программно созданные CardView с предпочтениями
     */
    private FlexboxLayout preferencesContainerLayout;

    /**
     * Содержит в себе весь список указанных предпочтений
     * Нужен для проверки выбора уже имеющихся предпочтений
     */
    private List<String> containedPref = new ArrayList<>();

    /**
     * Запускается при создании activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        preferencesContainerLayout = findViewById(R.id.flexbox);

        // Отрисовывем полученные с веба интересы
        if (Account.getInterests().size() != 0) {
            /**
             * Предпочтения, полученные из веба для аккаунта
             */
            List<String> prefs = Account.getInterests();
            containedPref.addAll(prefs);
            for(String str: prefs) {
                // Добавляем сделанный CardView в основной layout
                View prefView = drawPref().prefViewParam(str);
                preferencesContainerLayout.addView(prefView);
            }
        }

        // Открывает список со списком возможных предпочтений
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddPref(view);
            }
        });

        // Контактирует с полем Price
        RangeSlider seekBarPrice = findViewById(R.id.seekBarPrice);
        EditText minPrice = findViewById(R.id.minPrice);
        EditText maxPrice = findViewById(R.id.maxPrice);
        Price priceHandler;
        if (Account.getPrice_range().size() != 0) {
            priceHandler = new Price(
                    Account.getPrice_range().get(0),
                    Account.getPrice_range().get(1)
            );
            float v1 = Account.getPrice_range().get(0);
            float v2 = Account.getPrice_range().get(1);
            minPrice.setText(String.valueOf(v1));
            maxPrice.setText(String.valueOf(v2));
            seekBarPrice.setValues(v1, v2);
        } else {
            priceHandler = new Price(0, 5000);
            minPrice.setText(String.valueOf(0));
            maxPrice.setText(String.valueOf(5000));
        }
        priceHandler.handle(seekBarPrice, minPrice, maxPrice);

        // Нижняя левая кнопка отменяет изменения
        FloatingActionButton fab_decline = findViewById(R.id.fab_decline);
        fab_decline.setOnClickListener(view -> finish());

        // Нижняя правая кнопка с галочкой принимает изменения
        FloatingActionButton fab_accept = findViewById(R.id.fab_accept);
        fab_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account.updateInterests(containedPref);
                List<Float> vals = seekBarPrice.getValues();
                Account.setPrice_range(vals.get(0), vals.get(1));

                finish();
            }
        });
    }

    /**
     * Подготавливает CardView, в котором находятся строка с предпочтением и кнопка закрытия
     * @return - PreferenceBlock, в котором программно создаются и группируются нужные компоненты
     */
    private PreferenceBlock drawPref() {
        return new PreferenceBlock(
                new CardView(this),
                new ConstraintLayout(this),
                new TextView(this),
                new Button(this),
                getResources(),
                containedPref
        );
    }

    /**
     * Меню, которое вызывается при нажании на FloatingActionButton в блоке 'Предпочтения'
     * @param view - окошко (см. документацию) )
     */
    private void onAddPref(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.pref_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String selectedItem = Objects.requireNonNull(item.getTitle()).toString();

                if (containedPref.contains(selectedItem)) {
                    return false;
                } else {
                    containedPref.add(selectedItem);

                    // Добавляем сделанный CardView в основной layout
                    View prefView = drawPref().prefViewParam(selectedItem);
                    preferencesContainerLayout.addView(prefView);

                    // После каждого добавления предпочтения обновляется список предпочтений аккаунта
//                    Account.updateInterests(containedPref);

                    return true;
                }
            }
        });

        popupMenu.show();
    }
}