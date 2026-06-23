package com.example.cashregisterv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
public class ListDataAdapter extends BaseAdapter {

    private final ArrayList<Product> list;
    private final Context context;

    public ListDataAdapter(ArrayList<Product> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder holder;

        //Check if the program can recycle an existing view row instead of inflating a new one
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_row, container, false);

            // Initialize the ViewHolder to cache the view lookups
            holder = new ViewHolder();
            holder.productType = convertView.findViewById(R.id.list_type);
            holder.productPrice = convertView.findViewById(R.id.list_price);
            holder.productQty = convertView.findViewById(R.id.list_quantity);

            // Hang onto this holder object inside the view tag
            convertView.setTag(holder);
        } else {
            // If the view exists, then it'll pull the cached view lookups directly out of the tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Get current data item
        Product currentProduct = list.get(position);

        //Getter methods for the product class
        holder.productType.setText(currentProduct.getType());
        holder.productPrice.setText(String.valueOf(currentProduct.getPrice()));
        holder.productQty.setText(String.valueOf(currentProduct.getQuantity()));

        return convertView;
    }

    //Minimizing heavy usage of findViewById calls while scrolling through the UI
    private static class ViewHolder {
        TextView productType;
        TextView productPrice;
        TextView productQty;
    }
}