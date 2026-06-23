package com.example.cashregisterv2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;
public class DetailHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        // Hides the action bar completely for this screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        TextView typeTV = findViewById(R.id.myType);
        TextView priceTV = findViewById(R.id.myPrice);
        TextView dateTV = findViewById(R.id.myDate);

        //Verification on the history's past purchases
        int index = getIntent().getIntExtra("Index", -1);

        ArrayList<History> historyList = ((MyApp) getApplication()).getHistoryList();

        // Checks on whether the index is valid and within bounds before pulling the object
        if (index != -1 && index < historyList.size()) {
            History h1 = historyList.get(index);

            typeTV.setText(String.format("%s %s", typeTV.getText().toString(), h1.getType()));
            priceTV.setText(String.format("%s $%s", priceTV.getText().toString(), String.valueOf(h1.getPrice())));
            dateTV.setText(String.format("%s %s", dateTV.getText().toString(), String.valueOf(h1.getDate())));
        } else {
            // Safe fallback if the index was invalid
            typeTV.setText("Error: Transaction not found");
        }
    }
}