package com.example.assignment2_cashregister;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

// Restock class
// When user clicks the product name and enters new quantity, they can increase the quantity of the product.
// When Product is restocked, update shared preference
public class Restock extends AppCompatActivity implements ProductBaseAdapter.ProductListener{

    EditText newQuantityText;
    Button buttonOK;
    Button buttonCancel;
    ListView productListRestock;
    ProductManager serviceClass;
    ProductBaseAdapter adapter;
    SharedPreferences sharedPref;

    private int selectedPosition = -1; // Selected product list position
    private String productName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
        serviceClass = ((MyApp)getApplication()).service;
        sharedPref = this.getSharedPreferences("projectmanager", Context.MODE_PRIVATE);

        newQuantityText = findViewById(R.id.newQuantityText);
        buttonOK = findViewById(R.id.buttonOK);
        buttonCancel = findViewById(R.id.buttonCancel);
        productListRestock = findViewById(R.id.productListRestock);

        adapter = new ProductBaseAdapter(serviceClass.getProductList(), this);

        productListRestock.setAdapter(adapter);
        adapter.productListener = this;

        buttonOK.setOnClickListener(v -> {
            if(newQuantityText.getText().toString().isEmpty() || selectedPosition < 0) {
                Toast.makeText(Restock.this, getString(R.string.all_fields_are_required), Toast.LENGTH_LONG).show();

            } else {
                // Restock the product (index of product list)
                serviceClass.restockAt(selectedPosition, Integer.parseInt(newQuantityText.getText().toString()));

                // Toast a message with quantity and product name
                Toast.makeText(Restock.this, "Added " + newQuantityText.getText().toString() + " " + productName, Toast.LENGTH_LONG).show();

                // Update ProjectManager in the storage
                updateProjectManagerInStorage();

                adapter.notifyDataSetChanged();
            }
        });

        buttonCancel.setOnClickListener(v -> finish());
    }

    @Override
    public void nameClicked(int position, String name) {
        // Update the background color of the row when user clicks the name of the product.
        productListRestock.getChildAt(position).setBackgroundColor(ContextCompat.getColor(this, R.color.pink));

        if (selectedPosition != -1 && selectedPosition != position){
            // reset the previously selected row when user clicks the different row.
            productListRestock.getChildAt(selectedPosition).setBackgroundColor(ContextCompat.getColor(this, R.color.bgcolor));
        }
        selectedPosition = position;
        productName = name;
    }

    void updateProjectManagerInStorage(){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        //encoding
        String json = gson.toJson(((MyApp)getApplication()).service);
        editor.putString("productmanager",json);
        editor.apply();
    }
}