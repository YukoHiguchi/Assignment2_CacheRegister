package com.example.assignment2_cashregister;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductBaseAdapter extends BaseAdapter {

    interface ProductListener {
        void nameClicked(int position, String name);
    }
    ProductListener productListener;

    ArrayList<Product> list;
    Context context;

    public ProductBaseAdapter(ArrayList<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // LayoutInflater takes the layout id and provides the view object
            convertView = LayoutInflater.from(context).inflate(R.layout.product_row, parent, false);
        }
        TextView nameText = convertView.findViewById(R.id.productname);
        TextView priceText = convertView.findViewById(R.id.productprice);
        TextView quantityText = convertView.findViewById(R.id.productquantity);

        Product product = list.get(position);
        nameText.setText(product.getName());
        priceText.setText(Double.toString(product.getPrice()));
        quantityText.setText(Integer.toString(product.getQuantity()));

        nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListener.nameClicked(position, product.getName());
            }
        });

        return convertView;
    }

}
