package com.example.givegiftdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.givegiftdesign.preference.NumOfIdeas;
import com.example.givegiftdesign.preference.Price;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PreferenceActivity extends AppCompatActivity {

    private LinearLayout preferencesContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        preferencesContainerLayout = findViewById(R.id.pref_container);

        // Открывает список со списком возможных предпочтений
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddPref(view);
            }
        });

        CardView block = findViewById(R.id.pref1);
        Button deleteBtn = findViewById(R.id.btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup parent = (ViewGroup) block.getParent();
                parent.removeView(block);
            }
        });




        // Контактирует с полем Price
        SeekBar seekBarPrice = findViewById(R.id.seekBarPrice);
        EditText minPrice = findViewById(R.id.minPrice);
        EditText maxPrice = findViewById(R.id.maxPrice);
        Price priceHandler = new Price();
        priceHandler.handle(seekBarPrice, minPrice, maxPrice);

        // Контактирует с полем Number of ideas
        SeekBar seekBarCount = findViewById(R.id.seekBarNumOfIdeas);
        EditText countOf = findViewById(R.id.editTextNumOfIdeas);
        NumOfIdeas numOfIdeas = new NumOfIdeas();
        numOfIdeas.handle(seekBarCount, countOf);

        // Нижняя левя кнопка отменяет изменения
        FloatingActionButton fab_decline = findViewById(R.id.fab_decline);
        fab_decline.setOnClickListener(view -> finish());

        // Нижняя правая кнопка с галочкой принимает изменения
        FloatingActionButton fab_accept = findViewById(R.id.fab_accept);
        fab_accept.setOnClickListener(view -> finish());
    }

    private void onAddPref(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.pref_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String selectedItem = item.getTitle().toString();

                // Самый внешний элемент, который может задать border radius
                CardView cardViewBlock = new CardView(PreferenceActivity.this);
                final int cardMargin = 5;
                ViewGroup.MarginLayoutParams cardViewParams = new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                cardViewParams.setMargins(cardMargin, cardMargin, cardMargin, cardMargin);
                cardViewBlock.setLayoutParams(cardViewParams);
                final int cardCornerRadius = 15;
                cardViewBlock.setRadius(cardCornerRadius);

//                cardViewBlock.setId(View.generateViewId());
                //

                // В этом слое можно правильно расположить элементы
                ConstraintLayout innerBlock = new ConstraintLayout(PreferenceActivity.this);
                innerBlock.setLayoutParams(new CardView.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                innerBlock.setBackgroundColor(Color.parseColor("#fd6232"));
//                innerBlock.setId(View.generateViewId());
                //

                // Название предпочтения
                TextView pref = new TextView(PreferenceActivity.this);
                pref.setId(View.generateViewId());
                final int textPaddingStart = 12;
                pref.setPaddingRelative(textPaddingStart, 5, 0, 5);
                pref.setText(selectedItem);
                pref.setTextColor(Color.WHITE);
                final int textSize = 15;
                pref.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                pref.setTypeface(Typeface.create("serif", Typeface.NORMAL));

                // Кнопка для удаления предпочтения из внешнего layout
                Button closeBtn = new Button(PreferenceActivity.this);
                closeBtn.setId(View.generateViewId());
                closeBtn.setLayoutParams(new ConstraintLayout.LayoutParams(
                        40,
                        40
                ));
                closeBtn.setBackground(getResources().getDrawable(R.color.orange));
                closeBtn.setText(R.string.delete);
                closeBtn.setTextColor(getResources().getColor(R.color.white));

                // Установка ограничений в ConstraintLayout
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(innerBlock);
                constraintSet.connect(pref.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
                constraintSet.connect(pref.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(pref.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

                constraintSet.connect(closeBtn.getId(), ConstraintSet.LEFT, pref.getId(), ConstraintSet.RIGHT);
                constraintSet.connect(closeBtn.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                constraintSet.connect(closeBtn.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

                constraintSet.applyTo(innerBlock);

                innerBlock.addView(pref);
                innerBlock.addView(closeBtn);
                cardViewBlock.addView(innerBlock);

                preferencesContainerLayout.addView(cardViewBlock);

                return true;
            }
        });

        popupMenu.show();
    }


}