/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.teatime;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.teatime.util.TeaUtil;

import java.text.NumberFormat;

public class OrderActivity extends AppCompatActivity {


    private int mQuantity = 0;
    private int mTotalPrice = 0;



    private String mMilkType;
    private String mSugarType;
    private String mTeaName = "";

    private String mSize;

    public final static String EXTRA_TOTAL_PRICE = "com.example.android.teatime.EXTRA_TOTAL_PRICE";
    public final static String EXTRA_TEA_NAME = "com.example.android.teatime.EXTRA_TEA_NAME";
    public final static String EXTRA_SIZE = "com.example.android.teatime.EXTRA_SIZE";
    public final static String EXTRA_MILK_TYPE = "com.example.android.teatime.EXTRA_MILK_TYPE";
    public final static String EXTRA_SUGAR_TYPE = "com.example.android.teatime.EXTRA_SUGAR_TYPE";
    public final static String EXTRA_QUANTITY = "com.example.android.teatime.EXTRA_QUANTITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.order_toolbar);
        setSupportActionBar(menuToolbar);
        getSupportActionBar().setTitle(getString(R.string.order_title));

        // Set header name and image depending on which item was clicked in the gridView
        Intent intent = getIntent();
        mTeaName = intent.getStringExtra(MenuActivity.EXTRA_TEA_NAME);

        TextView teaNameTextView = (TextView) findViewById(R.id.tea_name_text_view);
        teaNameTextView.setText(mTeaName);

        // Set cost default to $0.00
        TextView costTextView = (TextView) findViewById(
                R.id.cost_text_view);
        costTextView.setText(getString(R.string.initial_cost));

        setupSizeSpinner();
        setupMilkSpinner();
        setupSugarSpinner();
    }

    /**
     * Sets up the dropdown spinner for user to select tea mSize
     */
    private void setupSizeSpinner() {

        Spinner mSizeSpinner = (Spinner) findViewById(R.id.tea_size_spinner);

        // Create an ArrayAdapter using the string array and a default mSizeSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tea_size_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the mSizeSpinner
        mSizeSpinner.setAdapter(adapter);

        // Set the integer mSelected to the constant values
        mSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSize = TeaUtil.getTeaSize(position, getResources());
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSize = TeaUtil.getTeaSize(0, getResources());
            }
        });

    }


    /**
     * Sets up the dropdown spinner for user to select milk type
     */
    private void setupMilkSpinner() {

        Spinner mSizeSpinner = (Spinner) findViewById(R.id.milk_spinner);

        // Create an ArrayAdapter using the string array and a default mSizeSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.milk_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the mSizeSpinner
        mSizeSpinner.setAdapter(adapter);

        // Set the integer mSelected to the constant values
        mSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMilkType = TeaUtil.getMilkType(position, getResources());
            }


            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMilkType = TeaUtil.getMilkType(0, getResources());
            }
        });

    }


    /**
     * Setup the dropdown spinner for user to select amount of sugar
     */
    private void setupSugarSpinner() {

        Spinner mSizeSpinner = (Spinner) findViewById(R.id.sugar_spinner);

        // Create an ArrayAdapter using the string array and a default mSizeSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sugar_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the mSizeSpinner
        mSizeSpinner.setAdapter(adapter);

        // Set the integer mSelected to the constant values
        mSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSugarType = TeaUtil.getSugarType(position, getResources());
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSugarType = TeaUtil.getSugarType(4, getResources());
            }
        });

    }

    /**
     * Increments the quantity and recalculates the price
     */
    public void increment(View view) {

        mQuantity = TeaUtil.increment(mQuantity);
        displayQuantity(mQuantity);
        mTotalPrice = calculatePrice();
        displayCost(mTotalPrice);
    }

    /**
     * Decrements the quantity and recalculates the price
     */
    public void decrement(View view) {
        if (mQuantity > 0) {

            mQuantity = TeaUtil.decrement(mQuantity);
            displayQuantity(mQuantity);
            mTotalPrice = calculatePrice();
            displayCost(mTotalPrice);
        }
    }


    /**
     * Calculates the TotalPrice of the order.
     *
     * @return total mTotalPrice
     */
    private int calculatePrice() {
        mTotalPrice = TeaUtil.getTeaPrice(mSize, mQuantity);
        return mTotalPrice;
    }

    /**
     * Displays the given mQuantity value on the screen then
     * calculates and displays the cost
     */
    private void displayQuantity(int numberOfTeas) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(numberOfTeas));
    }

    private void displayCost(int totalPrice) {
        TextView costTextView = (TextView) findViewById(
                R.id.cost_text_view);

        String convertPrice = NumberFormat.getCurrencyInstance().format(totalPrice);
        costTextView.setText(convertPrice);
    }

    /**
     * This method is called when the "Brew Tea" button is clicked
     * and a new intent opens the the {@link OrderSummaryActivity}
     */
    public void brewTea(View view) {
        // Create a new intent to open the {@link OrderSummaryActivity}
        Intent intent = new Intent(OrderActivity.this, OrderSummaryActivity.class);
        intent.putExtra(EXTRA_TOTAL_PRICE, mTotalPrice);
        intent.putExtra(EXTRA_TEA_NAME, mTeaName);
        intent.putExtra(EXTRA_SIZE, mSize);
        intent.putExtra(EXTRA_MILK_TYPE, mMilkType);
        intent.putExtra(EXTRA_SUGAR_TYPE, mSugarType);
        intent.putExtra(EXTRA_QUANTITY, mQuantity);

        startActivity(intent);
    }
}