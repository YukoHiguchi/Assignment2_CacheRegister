package com.example.assignment2_cashregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PurchaseHistoryViewHolder> {

    interface RecyclerHistoryClickListener {
        void historySelected(int position);
    }
    ArrayList<PurchasedProduct> list;
    Context context;

    RecyclerHistoryClickListener listener;

    // Initialize the list and context of the Adapter
    public RecyclerViewAdapter(ArrayList<PurchasedProduct> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerViewAdapter.PurchaseHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new PurchaseHistoryViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.PurchaseHistoryViewHolder holder, int position) {
        holder.productNameText.setText(list.get(position).getName());
        holder.productQuantityText.setText(String.format(Locale.CANADA, "%d", list.get(position).getQuantity()));
        holder.productTotalText.setText(String.format(Locale.CANADA, "%.2f", list.get(position).getTotalPrice()));
    }

    // Return the size of the list
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Provide a reference to the type of views using PurchaseHistoryViewHolder
    class PurchaseHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView productNameText;
        TextView productQuantityText;
        TextView productTotalText;

        public PurchaseHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameText = itemView.findViewById(R.id.productname_history);
            productQuantityText = itemView.findViewById(R.id.productquantity);
            productTotalText = itemView.findViewById(R.id.producttotalcost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.historySelected(getAdapterPosition());
                }
            });
        }
    }
}
