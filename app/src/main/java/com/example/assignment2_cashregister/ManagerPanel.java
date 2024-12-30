package com.example.assignment2_cashregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
// ManagerPanel class
// Display History and Restock buttons
public class ManagerPanel extends AppCompatActivity {

    Button buttonHistory;
    Button buttonRestock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_panel);
        buttonHistory = findViewById(R.id.buttonHistory);
        buttonRestock = findViewById(R.id.buttonRestock);;

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(ManagerPanel.this, History.class);
                startActivity(historyIntent);
            }
        });

        buttonRestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restockIntent = new Intent(ManagerPanel.this, Restock.class);
                startActivity(restockIntent);
            }
        });
    }

}