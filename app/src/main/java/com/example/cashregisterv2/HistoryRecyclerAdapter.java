package com.example.cashregisterv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder> {
    interface ItemListener {
        void onItemClicked(int pos);
    }
    //============================================================================================
    private final ArrayList<History> list;
    ItemListener listener;
    public HistoryRecyclerAdapter(ArrayList<History> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Gets the context from the parent view directly
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        History currentItem = list.get(position);
        holder.historyType.setText(currentItem.getType());
        holder.historyPrice.setText(String.valueOf(currentItem.getPrice()));
        holder.historyQuantity.setText(String.valueOf(currentItem.getQuantity()));
    }
    //============================================================================================
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views are private so that whenever the past purchases gets viewed, it only access this.
        private final TextView historyType;
        private final TextView historyPrice;
        private final TextView historyQuantity;
        //============================================================================================
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            historyType = itemView.findViewById(R.id.history_type);
            historyPrice = itemView.findViewById(R.id.history_price);
            historyQuantity = itemView.findViewById(R.id.history_quantity);
            itemView.setOnClickListener(this);
        }
        //============================================================================================
        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(position);
                }
            }
        }
    }
}