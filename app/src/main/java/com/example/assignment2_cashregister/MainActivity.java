package com.example.assignment2_cashregister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Locale;

// MainActivity class
// Manager button is in the menu
// User can buy the product by selecting the name on the row, and enters the amount.
// If the product is not selected, or and the quantity is not set, Toast an error message.
// If the quantity is more than stock, Toast an error message
// When user selects the name of the product, the background of the row will be updated, so that user
// can clearly see they select the product or not.
public class MainActivity extends AppCompatActivity implements ProductBaseAdapter.ProductListener  {
    ProductBaseAdapter adapter;
    Button button1;
    Button button2;

    Button button3;
    Button button4;
    Button button5;
    Button button6;

    Button button7;
    Button button8;
    Button button9;
    Button button0;

    Button buttonC;
    Button buttonBuy;

    TextView selectedProductText;
    TextView quantityText;
    TextView totalText;

    ProductManager serviceClass;
    ListView productList;
    private String quantityString = "";

    private int selectedPosition; // Selected product list position
    private Product product; // Selected product

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getSharedPreferences("projectmanager", Context.MODE_PRIVATE);
        readFromSharedPreferences();
        serviceClass = ((MyApp)getApplication()).service;

        selectedProductText = findViewById(R.id.selectedProduct);
        quantityText = findViewById(R.id.quantity);
        totalText = findViewById(R.id.total);

        productList = findViewById(R.id.productlist);

        adapter = new ProductBaseAdapter(serviceClass.getProductList(), this);

        // Setting adapter to the productList
        productList.setAdapter(adapter);

        adapter.productListener = this;

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button0 = findViewById(R.id.button0);
        buttonC = findViewById(R.id.buttonC);
        buttonBuy = findViewById(R.id.buyButton);

        button1.setOnClickListener(createListener("1"));
        button2.setOnClickListener(createListener("2"));
        button3.setOnClickListener(createListener("3"));
        button4.setOnClickListener(createListener("4"));
        button5.setOnClickListener(createListener("5"));
        button6.setOnClickListener(createListener("6"));
        button7.setOnClickListener(createListener("7"));
        button8.setOnClickListener(createListener("8"));
        button9.setOnClickListener(createListener("9"));
        button0.setOnClickListener(createListener("0"));

        buttonC.setOnClickListener(v -> {
            quantityString = "";
            quantityText.setText(quantityString);
            totalText.setText("0");
        });

        button0.setOnClickListener(v -> {
            if (quantityString.isEmpty()) {
                return;
            }
            quantityString += "0";
            quantityText.setText(quantityString);
            totalText.setText(String.format(Locale.CANADA,"%.2f", getTotalCost()));
        });

        // buttonBuy click listener
        // Validate the selected product and quantity, and then proceed purchase process
        buttonBuy.setOnClickListener(v -> {
            if(quantityString.isEmpty() || selectedProductText.getText().length() == 0) {
                // Missing requirement
                Toast.makeText(MainActivity.this, getString(R.string.all_fields_are_required), Toast.LENGTH_LONG).show();
                return;
            }
            int quantity = Integer.parseInt(quantityString);
            Product product = serviceClass.getProductList().get(selectedPosition);

            if (product.hasEnoughStock(quantity)) {
                // Purchase the product
                double total = serviceClass.purchase(product, quantity);
                totalText.setText(String.format(Locale.CANADA,"%.2f",total));

                createAlertDialog(getString(R.string.thank_you_for_your_purchase), String.format(Locale.CANADA,"Your purchase is %d %s for %.2f", quantity, product.getName(),total)).show();
                adapter.notifyDataSetChanged();
            } else {
                // Not enough product
                Toast.makeText(MainActivity.this, getString(R.string.no_enough_quantity_in_the_stock), Toast.LENGTH_LONG).show();
            }
        });
    }

    // concat the String to quantityString and update UI (quantity and total)
    private View.OnClickListener createListener(String buttonNumber) {
        return v -> {
            quantityString += buttonNumber;
            quantityText.setText(quantityString);
            totalText.setText(String.format(Locale.CANADA,"%.2f", getTotalCost()));
        };
    }

    private AlertDialog createAlertDialog(String dialog_title, String dialog_message) {
        // 1. Instantiate an AlertDialog.Builder with its constructor.
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(dialog_message)
                .setTitle(dialog_title)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Update storage
                    updateProductManagerInStorage();

                    // initialize UI
                    productList.getChildAt(selectedPosition).setBackgroundColor(ContextCompat.getColor(this, R.color.bgcolor));
                    quantityString = "";
                    selectedProductText.setText("");
                    totalText.setText("");
                    quantityText.setText("");
                    product = null;
                    selectedPosition = -1;
                });

        // 3. Get the AlertDialog.
        return builder.create();
    }
    @Override
    public void nameClicked(int position, String name) {
        // set the selected Product name
        selectedProductText.setText(name);
        // When user click product name, update the background of the row
        productList.getChildAt(position).setBackgroundColor(ContextCompat.getColor(this, R.color.pink));

        if (selectedPosition != -1 && selectedPosition != position){
            // Reset the background color of the previously selected row
            productList.getChildAt(selectedPosition).setBackgroundColor(ContextCompat.getColor(this, R.color.bgcolor));
        }
        selectedPosition = position;
        product = serviceClass.getProductList().get(selectedPosition);
        // Set the total price text
        totalText.setText(String.format(Locale.CANADA,"%.2f", getTotalCost()));
    }

    // Get total cost from product and quantity
    private double getTotalCost() {
        if (product == null || quantityString.isEmpty()) {
            return 0.0;
        } else {
            return Integer.parseInt(quantityString) * product.getPrice();
        }
    }

    // Update "projectmanager" value in Shared Preferences
    void updateProductManagerInStorage(){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        // convert service to string(JSON)
        String json = gson.toJson(((MyApp)getApplication()).service);
        editor.putString("productmanager", json);
        editor.apply();
    }

    // Read from Shared Preferences
    void readFromSharedPreferences() {

        String jsonfromPreferences = sharedPref.getString("productmanager", "");
        Gson gson = new Gson();

        // decoding
        ProductManager pm = gson.fromJson(jsonfromPreferences, new TypeToken<ProductManager>() {
        }.getType());

        if (pm != null) {
            // Set the data to service
            ((MyApp) getApplication()).service = pm;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.mainactivity_menu,menu);
        return true;

    }

    // Manager menu item is selected, navigate to the ManagerPanel class
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.manager_menu) {
            Intent managerIntent = new Intent(MainActivity.this, ManagerPanel.class);
            startActivity(managerIntent);
        }
        return true;
    }
}