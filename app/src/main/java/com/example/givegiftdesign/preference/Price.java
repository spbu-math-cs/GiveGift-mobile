package com.example.givegiftdesign.preference;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Price {
//    private final int MULT = 1500;
    private List<Float> values;
    public void handle(RangeSlider rangeSlider, EditText minPrice, EditText maxPrice) {
        // Изменяет значение в EditText
        rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                values = slider.getValues();
                minPrice.setText(String.valueOf(values.get(0)));
                maxPrice.setText(String.valueOf(values.get(1)));
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                values = slider.getValues();
                minPrice.setText(String.valueOf(values.get(0)));
                maxPrice.setText(String.valueOf(values.get(1)));
            }
        }
    );

//        rangeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                maxPrice.setText(String.valueOf(progress * MULT));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        List<Float> val = new ArrayList<>();
        val.add(0f);
        val.add(0f);

        // Изменяет ползунок в зависимости от значения в EditText
        minPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (s.toString().isEmpty()) {
//                    val.set(0, Float.valueOf(String.valueOf(minPrice.getText())));
////                    val.add(Float.valueOf(String.valueOf(maxPrice.getText())));
//                    rangeSlider.setValues(val);
//                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                minPrice.setSelection(minPrice.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!s.toString().isEmpty()) {
//                    val.set(0, Float.valueOf(String.valueOf(minPrice.getText())));
////                    val.add(Float.valueOf(String.valueOf(maxPrice.getText())));
//                    rangeSlider.setValues(val);
//                }
            }
        });

        // Изменяет ползунок в зависимости от значения в EditText
        maxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                seekBarPrice.setProgress(start);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!s.toString().isEmpty()) {
//                    List<Float> val = new ArrayList<>();
//                    val.add(Float.valueOf(String.valueOf(minPrice.getText())));
//                    val.set(1, Float.valueOf(String.valueOf(maxPrice.getText())));
//                    rangeSlider.setValues(val);
//                }
            }
        });
    }
}
