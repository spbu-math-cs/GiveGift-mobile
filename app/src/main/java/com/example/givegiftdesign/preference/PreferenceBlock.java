package com.example.givegiftdesign.preference;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.givegiftdesign.R;

public class PreferenceBlock extends AppCompatActivity {
    public PreferenceBlock(CardView cardViewBlock, ConstraintLayout innerBlock, TextView pref, Button closeBtn) {
        this.cardViewBlock = cardViewBlock;
        this.innerBlock = innerBlock;
        this.pref = pref;
        this.closeBtn = closeBtn;

        setPrefBlock();
    }

    CardView cardViewBlock;
    ConstraintLayout innerBlock;
    TextView pref;
    Button closeBtn;

    public View prefViewParam(String selectedItem) {
        pref.setText(selectedItem);

        return cardViewBlock;
    }

    private void setPrefBlock() {
        Resources resources = getResources();

        // Самый внешний элемент, который может задать border radius
        final int cardMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(R.dimen.pref_cardview_margin),
                resources.getDisplayMetrics()
                );
        final int cardCornerRadius = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(R.dimen.pref_cardview_cornerradius),
                resources.getDisplayMetrics()
        );
        ViewGroup.MarginLayoutParams cardViewParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        cardViewParams.setMargins(cardMargin, cardMargin, cardMargin, cardMargin);
        cardViewBlock.setLayoutParams(cardViewParams);
        cardViewBlock.setRadius(cardCornerRadius);
        //

        // В этом слое можно правильно расположить элементы
        innerBlock.setLayoutParams(new CardView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        innerBlock.setBackgroundColor(Color.parseColor(String.valueOf(R.color.orange)));
        //

        // Название предпочтения
        final int textPaddingStart = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(R.dimen.pref_textview_paddingstart),
                resources.getDisplayMetrics()
        );
        final int textSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(R.dimen.pref_textview_textsize),
                resources.getDisplayMetrics()
        );
        pref.setId(View.generateViewId());
        pref.setPaddingRelative(textPaddingStart, 5, 0, 5);
        pref.setTextColor(Color.WHITE);
        pref.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        pref.setTypeface(Typeface.create("serif", Typeface.NORMAL));
        //

        // Кнопка для удаления предпочтения из внешнего layout
        final int btnSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                resources.getDimension(R.dimen.pref_button_width),
                resources.getDisplayMetrics()
        );
        closeBtn.setId(View.generateViewId());
        closeBtn.setLayoutParams(new ConstraintLayout.LayoutParams(
                btnSize,
                btnSize
        ));
        closeBtn.setBackground(resources.getDrawable(R.color.orange));
        closeBtn.setOutlineProvider(null);
        closeBtn.setStateListAnimator(null);
        closeBtn.setText(R.string.delete);
        closeBtn.setTextColor(resources.getColor(R.color.white));
        //

        // Сразу добавляем элементы в ContraintLayout, чтобы можно было
        // применить ограничения на расположение
        innerBlock.addView(pref);
        innerBlock.addView(closeBtn);

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
        cardViewBlock.addView(innerBlock);
    }
}
