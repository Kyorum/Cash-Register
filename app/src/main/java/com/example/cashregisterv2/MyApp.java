package com.example.cashregisterv2;

import android.app.Application;
import java.util.ArrayList;

public class MyApp extends Application {

    //Encapuslation on list references
    private final ArrayList<Product> productList = new ArrayList<>();
    private final ArrayList<History> historyList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //Populates the initial inventory
        initializeProductListData();
    }

    // Public getter methods so other components can cleanly access the data
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public ArrayList<History> getHistoryList() {
        return historyList;
    }

    private void initializeProductListData() {
        if (productList.isEmpty()) {
            productList.add(new Product("Board Game", 16.95, 10));
            productList.add(new Product("Game Console", 60.20, 6));
            productList.add(new Product("Game Controllers", 33.59, 15));
            productList.add(new Product("HD Cables", 29.99, 30));
            productList.add(new Product("CD Disc", 24.99, 60));
            productList.add(new Product("Movie Case", 22.99, 20));
            productList.add(new Product("Sony CD Case", 29.99, 40));
        }
    }
}