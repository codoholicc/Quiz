package com.example.testseries.utils;

public class ColorPicker {
    private static final String[] colors = {"#3EB9DF", "#3E863D", "#D36280", "#E44F55", "#FA8056", "#8180CA", "#7D659F", "#51BAB3", "#4FB66C", "#E3AD17", "#627991", "#EF8EAD", "#B5BF6C"};
    private static int currentColorIndex = 0;

    public static String getColor() {
        currentColorIndex = (currentColorIndex + 1) % colors.length;
        return colors[currentColorIndex];
    }
}
