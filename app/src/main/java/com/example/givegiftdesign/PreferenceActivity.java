package com.example.givegiftdesign;

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

import com.example.givegiftdesign.preference.PreferenceBlock;
import com.example.givegiftdesign.preference.Price;
import com.example.givegiftdesign.request.Account;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PreferenceActivity extends AppCompatActivity {
    private FlexboxLayout preferencesContainerLayout;
    private ArrayList<String> containedPref = new ArrayList<>();
    private List<String> prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        preferencesContainerLayout = findViewById(R.id.flexbox);

        // Отрисовывем полученные с веба интересы
        prefs = Account.getInterests();
        containedPref.addAll(prefs);
        for(String str: prefs) {
            // Добавляем сделанный CardView в основной layout
            View prefView = drawPref().prefViewParam(str);
            preferencesContainerLayout.addView(prefView);
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
        SeekBar seekBarPrice = findViewById(R.id.seekBarPrice);
        EditText minPrice = findViewById(R.id.minPrice);
        EditText maxPrice = findViewById(R.id.maxPrice);
        Price priceHandler = new Price();
        priceHandler.handle(seekBarPrice, minPrice, maxPrice);

        // Нижняя левя кнопка отменяет изменения
        FloatingActionButton fab_decline = findViewById(R.id.fab_decline);
        fab_decline.setOnClickListener(view -> finish());

        // Нижняя правая кнопка с галочкой принимает изменения
        FloatingActionButton fab_accept = findViewById(R.id.fab_accept);
        fab_accept.setOnClickListener(view -> finish());
    }

    // Форма для заготовки CardView с предпочтением
    private PreferenceBlock drawPref() {
        // Самый внешний элемент, который может задать border radius
        CardView cardViewBlock = new CardView(PreferenceActivity.this);
        // В этом слое можно правильно расположить элементы
        ConstraintLayout innerBlock = new ConstraintLayout(PreferenceActivity.this);
        // Название предпочтения
        TextView pref = new TextView(PreferenceActivity.this);
        // Кнопка для удаления предпочтения из внешнего layout
        Button closeBtn = new Button(PreferenceActivity.this);

        PreferenceBlock prefBlock = new PreferenceBlock(
                cardViewBlock,
                innerBlock,
                pref,
                closeBtn,
                getResources(),
                containedPref
        );

        return prefBlock;
    }

    private void onAddPref(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.pref_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String selectedItem = item.getTitle().toString();

                if (containedPref.contains(selectedItem)) {
                    return false;
                } else {
                    containedPref.add(selectedItem);

                    // Добавляем сделанный CardView в основной layout
                    View prefView = drawPref().prefViewParam(selectedItem);
                    preferencesContainerLayout.addView(prefView);

                    return true;
                }
            }
        });

        popupMenu.show();
    }
}