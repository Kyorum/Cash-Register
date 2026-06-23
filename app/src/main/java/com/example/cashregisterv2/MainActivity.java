package com.example.cashregisterv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button one, two, three, four, five, six, seven, eight, nine, zero, clear, buy, managerBtn;
    private TextView typeTV, quantityTV, totalTV;
    private ListDataAdapter adapter;
    private Double total = 0.0;
    private ListView itemsListView;
    private int index = -1;
    private ArrayList<Product> stockList = new ArrayList<>();
    //============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hides the action bar completely for this screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setClickListeners();
        setListData();

        itemsListView.setOnItemClickListener(this);
    }
    //============================================================================================
    private void setClickListeners() {
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        buy.setOnClickListener(this);
        clear.setOnClickListener(this);
        managerBtn.setOnClickListener(this);
    }
    //============================================================================================
    private void setListData() {
        // Data is initialized automatically inside MyApp onCreate
        MyApp app = (MyApp) getApplication();
        stockList = app.getProductList();

        adapter = new ListDataAdapter(stockList, MainActivity.this);
        itemsListView.setAdapter(adapter);
    }
    //============================================================================================
    private void initViews() {
        typeTV = findViewById(R.id.product_textview);
        quantityTV = findViewById(R.id.quantity_tv);
        totalTV = findViewById(R.id.total_tv);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        clear = findViewById(R.id.clear_but);
        buy = findViewById(R.id.buttonBuy);
        managerBtn = findViewById(R.id.buttonManager);
        itemsListView = findViewById(R.id.listview_products);
    }
    //============================================================================================
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.buttonBuy) {
            //Verification on the products's total amount to equal the total price
            if (!totalTV.getText().toString().trim().isEmpty() && index != -1) {
                showAlert();
            } else {
                Toast.makeText(this, "Please select a product and enter quantity", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.clear_but) {
            resetInputs();
        }
        else if (id == R.id.buttonManager) {
            Intent intentManager = new Intent(MainActivity.this, ManagerActivity.class);
            startActivity(intentManager);
        }
        else { // Number pad targets (0-9)
            Button b = (Button) view;
            String num1 = quantityTV.getText().toString();
            String num2 = b.getText().toString();
            String num = num1 + num2;

            quantityTV.setText(num);

            //Array selection around the products' current stock
            if (index != -1 && index < stockList.size()) {
                int enteredQty = Integer.parseInt(num);
                int availableQty = stockList.get(index).getQuantity();

                if (enteredQty > availableQty) {
                    Toast.makeText(this, "There isn't enough products in the stock", Toast.LENGTH_SHORT).show();
                    resetInputs();
                } else {
                    Double price = stockList.get(index).getPrice();
                    total = price * enteredQty;
                    totalTV.setText(String.format("%.2f", total));
                }
            }
        }
    }
    //============================================================================================
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i >= 0 && i < stockList.size()) {
            typeTV.setText(stockList.get(i).getType());
            index = i;

            String qtyStr = quantityTV.getText().toString().trim();
            if (!qtyStr.isEmpty()) {
                total = Double.parseDouble(qtyStr) * stockList.get(i).getPrice();
                totalTV.setText(String.format("%.2f", total));
            }
        }
    }
    //============================================================================================
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MyApp app = (MyApp) getApplication();
        Product obj = stockList.get(index);

        String purchaseQty = quantityTV.getText().toString();
        String purchaseTotal = totalTV.getText().toString();

        builder.setMessage("Your purchase is " + purchaseQty + " " + obj.getType() + " for " + purchaseTotal)
                .setTitle("Thank You for your purchase");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                resetInputs();
            }
        });

        builder.create().show();

        // Save transactional updates back to application scope models safely
        int remainingQty = obj.getQuantity() - Integer.parseInt(purchaseQty);
        obj.setQuantity(remainingQty);
        app.getProductList().set(index, obj);
        adapter.notifyDataSetChanged();

        // Log transaction record data structures globally
        Date d1 = new Date();
        History h1 = new History(obj.getType(), Double.parseDouble(purchaseTotal), Integer.parseInt(purchaseQty), d1);
        app.getHistoryList().add(h1);
    }
    //============================================================================================
    //The input block
    private void resetInputs() {
        quantityTV.setText("");
        quantityTV.setHint("Quantity");
        totalTV.setText("");
        totalTV.setHint("Total");
        typeTV.setText("");
        typeTV.setHint("Product Type");
        index = -1;
        total = 0.0;
    }
    //============================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}