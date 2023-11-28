package com.example.givegiftdesign.preference;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;

public class Price {
    private final int MULT = 1500;
    public void handle(SeekBar seekBarPrice, EditText minPrice, EditText maxPrice) {
        // Изменяет значение в EditText
        seekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maxPrice.setText(String.valueOf(progress * MULT));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Изменяет ползунок в зависимости от значения в EditText
        minPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minPrice.setSelection(minPrice.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                if (!s.toString().isEmpty()) {
                    int progress = Integer.parseInt(s.toString()) / MULT;
                    seekBarPrice.setProgress(progress);
                    maxPrice.setSelection(maxPrice.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
