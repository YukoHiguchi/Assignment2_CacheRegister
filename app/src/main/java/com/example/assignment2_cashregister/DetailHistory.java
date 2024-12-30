package com.example.assignment2_cashregister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailHistory extends AppCompatActivity {

    TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        details = findViewById(R.id.purchasedetails);
        Intent intent = getIntent();
        PurchasedProduct product = (PurchasedProduct)intent.getSerializableExtra("product");
        String str = String.format("Product: %s\nPrice: %.2f\n Purchase Date: %s", product.getName(),product.getTotalPrice(), product.getTimestamp().toString());
        details.setText(str);
    }
}