package com.example.drugiekran_03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CYKL_ZYCIA";

    private ActivityResultLauncher<Intent> orderLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity: onCreate");

        setContentView(R.layout.activity_main);

        Spinner spPizza = findViewById(R.id.spPizza);
        Spinner spSize = findViewById(R.id.spSize);

        ArrayAdapter<CharSequence> pizzaAdapter = ArrayAdapter.createFromResource(
                this, R.array.pizzas, android.R.layout.simple_spinner_item);
        pizzaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPizza.setAdapter(pizzaAdapter);

        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(
                this, R.array.sizes, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSize.setAdapter(sizeAdapter);

        EditText etAddress = findViewById(R.id.etAddress);

        CheckBox cbCheese = findViewById(R.id.cbCheese);
        CheckBox cbHam = findViewById(R.id.cbHam);
        CheckBox cbMushrooms = findViewById(R.id.cbMushrooms);
        CheckBox cbExpress = findViewById(R.id.cbExpress);

        orderLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Toast.makeText(this, "Zamówienie przyjęte!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Zamówienie nie zostało potwierdzone",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            String address = etAddress.getText().toString().trim();

            if (address.isEmpty()) {
                etAddress.setError("Podaj adres dostawy");
                return;
            }

            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);

            intent.putExtra(SummaryActivity.EXTRA_PIZZA, spPizza.getSelectedItem().toString());
            intent.putExtra(SummaryActivity.EXTRA_SIZE, spSize.getSelectedItemPosition());
            intent.putExtra(SummaryActivity.EXTRA_ADDRESS, address);
            intent.putExtra(SummaryActivity.EXTRA_CHEESE, cbCheese.isChecked());
            intent.putExtra(SummaryActivity.EXTRA_HAM, cbHam.isChecked());
            intent.putExtra(SummaryActivity.EXTRA_MUSHROOMS, cbMushrooms.isChecked());
            intent.putExtra(SummaryActivity.EXTRA_EXPRESS, cbExpress.isChecked());

            orderLauncher.launch(intent);
        });

        Button btnAbout = findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity: onDestroy");
    }
}
