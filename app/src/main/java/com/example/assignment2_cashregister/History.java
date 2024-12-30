package com.example.assignment2_cashregister;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class History extends AppCompatActivity implements RecyclerViewAdapter.RecyclerHistoryClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        RecyclerView recyclerView = findViewById(R.id.recycler_list);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(
                ((MyApp)getApplication()).service.getRecords(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.listener = this;
    }

    @Override
    public void historySelected(int position) {
        Intent detailIntent = new Intent(this, DetailHistory.class);
        PurchasedProduct product = ((MyApp)getApplication()).service.getRecords().get(position);
        detailIntent.putExtra("product", product);
        startActivity(detailIntent);
    }
}