package com.example.android.teatime.util;

import android.content.res.Resources;

import com.example.android.teatime.R;

/**
 * Created by surajitbiswas on 1/1/19.
 */

public class TeaUtil {

    public static final String TEA_SIZE_SMALL = "Small ($5/cup)";
    public static final String TEA_SIZE_MEDIUM = "Medium ($6/cup)";
    public static final String TEA_SIZE_LARGE = "Large ($7/cup)";

    public static final int SMALL_PRICE = 5;
    public static final int MEDIUM_PRICE = 6;
    public static final int LARGE_PRICE = 7;

    public static String getTeaSize(int position, Resources res) {
        String teaSize = "";

        switch (position) {
            case 0:
                teaSize = res.getString(R.string.tea_size_small);
                break;
            case 1:
                teaSize = res.getString(R.string.tea_size_medium);
                break;
            case 2:
                teaSize = res.getString(R.string.tea_size_large);
                break;

        }
        return teaSize;
    }

    public static String getMilkType(int position, Resources res) {
        String milkType = "";

        switch (position) {
            case 0:
                milkType = res.getString(R.string.milk_type_none);
                break;
            case 1:
                milkType = res.getString(R.string.milk_type_nonfat);
                break;
            case 2:
                milkType = res.getString(R.string.milk_type_1_percent);
                break;
            case 3:
                milkType = res.getString(R.string.milk_type_2_percent);
                break;
            case 4:
                milkType = res.getString(R.string.milk_type_whole);
                break;
        }
        return milkType;
    }

    public static String getSugarType(int position, Resources res) {
        String sugarType = "";

        switch (position) {
            case 0:
                sugarType = res.getString(R.string.sweet_type_0);
                break;
            case 1:
                sugarType = res.getString(R.string.sweet_type_25);
                break;
            case 2:
                sugarType = res.getString(R.string.sweet_type_50);
                break;
            case 3:
                sugarType = res.getString(R.string.sweet_type_75);
                break;
            case 4:
                sugarType = res.getString(R.string.sweet_type_100);
                break;
        }
        return sugarType;
    }

    public static int increment(int value) {
        return ++value;
    }

    public static int decrement(int value) {
        return --value;
    }

    public static int getTeaPrice(String teaSize, int quantity) {
        int teaPrice = 0;

        // Calculate the total order mTotalPrice by multiplying by the mQuantity
        switch (teaSize) {
            case TEA_SIZE_SMALL:
                teaPrice = quantity * SMALL_PRICE;
                break;
            case TEA_SIZE_MEDIUM:
                teaPrice = quantity * MEDIUM_PRICE;
                break;
            case TEA_SIZE_LARGE:
                teaPrice = quantity * LARGE_PRICE;
                break;
        }

        return teaPrice;
    }
}
