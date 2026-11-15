package com.example.drugiekran_03;

import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    private static final String TAG = "CYKL_ZYCIA";

    public static final String EXTRA_PIZZA = "PIZZA";
    public static final String EXTRA_SIZE = "SIZE";
    public static final String EXTRA_ADDRESS = "ADDRESS";
    public static final String EXTRA_CHEESE = "CHEESE";
    public static final String EXTRA_HAM = "HAM";
    public static final String EXTRA_MUSHROOMS = "MUSHROOMS";
    public static final String EXTRA_EXPRESS = "EXPRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SummaryActivity: onCreate");

        setContentView(R.layout.activity_summary);

        Intent data = getIntent();

        String pizza = data.getStringExtra(EXTRA_PIZZA);
        int size = data.getIntExtra(EXTRA_SIZE, 0);
        String address = data.getStringExtra(EXTRA_ADDRESS);

        boolean cheese = data.getBooleanExtra(EXTRA_CHEESE, false);
        boolean ham = data.getBooleanExtra(EXTRA_HAM, false);
        boolean mushrooms = data.getBooleanExtra(EXTRA_MUSHROOMS, false);
        boolean express = data.getBooleanExtra(EXTRA_EXPRESS, false);

        String[] sizes = {"Mała", "Średnia", "Duża"};
        int[] prices = {25, 32, 40};

        int price = prices[size];

        StringBuilder additions = new StringBuilder();

        if (cheese) {
            additions.append("Dodatkowy ser\n");
            price += 4;
        }

        if (ham) {
            additions.append("Szynka\n");
            price += 5;
        }

        if (mushrooms) {
            additions.append("Pieczarki\n");
            price += 3;
        }

        if (express) {
            additions.append("Dostawa ekspresowa\n");
            price += 8;
        }

        if (additions.length() == 0) {
            additions.append("Brak");
        }

        price += getPizzaExtra(pizza);

        String summary = "Pizza: " + pizza +
                "\nRozmiar: " + sizes[size] +
                "\nDodatki:\n" + additions +
                "\nAdres: " + address +
                "\n\nCena: " + price + " zł";

        TextView tvSummary = findViewById(R.id.tvSummary);
        tvSummary.setText(summary);

        findViewById(R.id.btnEdit).setOnClickListener(v -> finish());

        findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        findViewById(R.id.btnShare).setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, summary);

            try {
                startActivity(Intent.createChooser(shareIntent, "Udostępnij zamówienie"));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Brak aplikacji do udostępniania",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getPizzaExtra(String pizza) {
        if (pizza.equals("Pepperoni")) return 3;
        if (pizza.equals("Capricciosa")) return 5;
        if (pizza.equals("Hawajska")) return 4;
        return 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "SummaryActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SummaryActivity: onDestroy");
    }
}
