package com.example.cashregisterv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class ShowHistoryActivity extends AppCompatActivity implements HistoryRecyclerAdapter.ItemListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_history);

        // Hides the action bar completely for this screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        RecyclerView recyclerView = findViewById(R.id.historyRecyclerView);

        HistoryRecyclerAdapter adapter = new HistoryRecyclerAdapter(((MyApp) getApplication()).getHistoryList());
        adapter.listener = this;
        recyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
    }
    //============================================================================================
    @Override
    public void onItemClicked(int pos) {
        Intent intent = new Intent(ShowHistoryActivity.this, DetailHistoryActivity.class);
        intent.putExtra("Index", pos);
        startActivity(intent);
    }
}
