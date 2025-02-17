package com.example.testseries.utils;

import com.example.testseries.R;

public class IconPicker {
    private static final int[] icons = {
            R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.icon3,
            R.drawable.icon4,
            R.drawable.icon5,
            R.drawable.icon6,
            R.drawable.icon7,
            R.drawable.icon8,

    };
    private static int currentIcon = 0;

    public static int getIcon() {
        currentIcon = (currentIcon + 1) % icons.length;
        return icons[currentIcon];
    }
}
