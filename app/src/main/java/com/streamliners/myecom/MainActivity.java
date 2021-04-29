package com.streamliners.myecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.streamliners.myecom.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private int qty = 0;
    private int minVal, maxVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupEventHandlers();
        getInitialCount();
    }

    private void getInitialCount(){
        //Get data from intent
        Bundle bundle = getIntent().getExtras();
       qty = bundle.getInt(Constants.INITIAL_COUNT_KEY, 0);
       minVal = bundle.getInt(Constants.MINIMUM_VALUE, Integer.MIN_VALUE);
       maxVal = bundle.getInt(Constants.MAXIMUM_VALUE, Integer.MAX_VALUE);
       b.qty.setText(String.valueOf(qty));

       if(qty != 0){
           b.sendBackBtn.setVisibility(View.VISIBLE);
       }

    }

    private void setupEventHandlers() {
        b.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incQty();
            }

        });
        b.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decQty();
            }
        });
    }


    public void decQty() {
        b.qty.setText("" + --qty);
    }


    public void incQty() {
        b.qty.setText("" + ++qty);
    }

    public void sendDataBack(View view) {
        //Validate count
        if(qty >= minVal && qty <= maxVal){

            //Send the data
            Intent intent = new Intent();
            intent.putExtra(Constants.FINAL_COUNT, qty);
            setResult(RESULT_OK, intent);

            //Close the Activity
            finish();
        }
        //Not in range
        else{
            Toast.makeText(this, "Not in range!", Toast.LENGTH_SHORT).show();
        }
    }
}
