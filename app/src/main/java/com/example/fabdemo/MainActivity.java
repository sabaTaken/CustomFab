package com.example.fabdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout fabItemsLayout;
    private ConstraintLayout fabBackgroundLayout;
    private boolean isFabOpen = false;
    private ImageView fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabItemsLayout = findViewById(R.id.fabItemsLayout);
        fabBackgroundLayout = findViewById(R.id.fabBackgroundLayout);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            if (isFabOpen) closeFabMenu();
            else openFabMenu();
        });
        findViewById(R.id.fabItemTwitter).setOnClickListener(v -> onTwitterClick());
        findViewById(R.id.fabItemInstagram).setOnClickListener(v -> onInstaClick());
        findViewById(R.id.fabItemTelegram).setOnClickListener(v -> onTelegramClick());
    }

    private void openFabMenu() {
        toggleFabBackColor(
                ContextCompat.getColor(this, android.R.color.transparent),
                ContextCompat.getColor(this, R.color.colorFabBackLayout)
        );
        toggleFabMenu(0, 250);
        rotateFabMenu(0, 180);
        isFabOpen = true;
    }

    private void closeFabMenu() {
        toggleFabBackColor(
                ContextCompat.getColor(this, R.color.colorFabBackLayout),
                ContextCompat.getColor(this, android.R.color.transparent)
        );
        toggleFabMenu(250, 0);
        rotateFabMenu(180, 0);
        isFabOpen = false;
    }

    private void toggleFabMenu(int fromHeight, int toHeight) {
        ValueAnimator m1 = ValueAnimator.ofInt(
                convertDpToPixel(fromHeight, this), convertDpToPixel(toHeight, this));
        m1.setDuration(200);
        m1.setInterpolator(new LinearInterpolator());
        m1.addUpdateListener(animation -> {
            ((ConstraintLayout.LayoutParams) fabItemsLayout.getLayoutParams()).height =
                    (int) animation.getAnimatedValue();
            fabItemsLayout.requestLayout();
        });
        m1.start();
    }

    private void rotateFabMenu(float fromDegree, float toDegree) {
        ValueAnimator m1 = ValueAnimator.ofFloat(fromDegree, toDegree);
        m1.setDuration(200);
        m1.setInterpolator(new LinearInterpolator());
        m1.addUpdateListener(animation -> {
            fab.setRotation((float) animation.getAnimatedValue());
            fabItemsLayout.requestLayout();
        });
        m1.start();
    }

    private void toggleFabBackColor(int fromColor, int toColor) {
        ValueAnimator m1 = ValueAnimator.ofArgb(fromColor, toColor);
        m1.setDuration(200);
        m1.setInterpolator(new LinearInterpolator());
        m1.addUpdateListener(animation -> {
            fabBackgroundLayout.setBackgroundColor((int) animation.getAnimatedValue());
            fabBackgroundLayout.requestLayout();
        });
        m1.start();
    }

    private void onTwitterClick() {
        Toast.makeText(this, "Twitter selected", Toast.LENGTH_SHORT).show();
    }

    private void onInstaClick() {
        Toast.makeText(this, "Instagram selected", Toast.LENGTH_SHORT).show();
    }

    private void onTelegramClick() {
        Toast.makeText(this, "Telegram selected", Toast.LENGTH_SHORT).show();
    }

    private static int convertPixelsToDp(float px, Context context) {
        return (int) (px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertDpToPixel(float dp, Context context) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
