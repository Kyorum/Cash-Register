package com.example.cashregisterv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class RestockActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private EditText quantityET;
    private Button okBtn, cancelBtn;
    private ListView restockListView;
    private ListDataAdapter adapter;
    private int selectedIndex = -1;
    //============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        // Hides the action bar completely for the restock screen
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeAllViews();
        setListData();
        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        restockListView.setOnItemClickListener(this);
    }

    private void setListData(){
        adapter = new ListDataAdapter(((MyApp) getApplication()).getProductList(), RestockActivity.this);
        restockListView.setAdapter(adapter);
    }

    private void initializeAllViews(){
        quantityET = findViewById(R.id.editTextQuantity);
        okBtn = findViewById(R.id.okButtonR);
        cancelBtn = findViewById(R.id.cancelButtonR);
        restockListView = findViewById(R.id.restockListView);
    }
    //============================================================================================
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.okButtonR) {
            //Validation on input quantity
            String inputQuantity = quantityET.getText().toString().trim();

            if (inputQuantity.isEmpty() || selectedIndex == -1){
                Toast.makeText(this, "All fields are required to be filled", Toast.LENGTH_SHORT).show();
            } else {
                Product p1 = ((MyApp)getApplication()).getProductList().get(selectedIndex);
                p1.setQuantity(p1.getQuantity() + Integer.parseInt(inputQuantity));
                ((MyApp)getApplication()).getProductList().set(selectedIndex, p1);
                adapter.notifyDataSetChanged();
                selectedIndex = -1;
                quantityET.setText("");
            }
        }
        else {
            finish();
        }
    }
    //============================================================================================
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectedIndex = i;
        Toast.makeText(this, ((MyApp) getApplication()).getProductList().get(i).getType(), Toast.LENGTH_SHORT).show();
    }
}