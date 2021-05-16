package com.streamliners.intentsplayground;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.streamliners.intentsplayground.databinding.ActivityCounterBinding;


public class CounterActivity extends AppCompatActivity {

    private ActivityCounterBinding b;
    private int qty = 0;
    private int minVal, maxVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To create layout using layout inflater
        b = ActivityCounterBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupEventHandlers();
        getInitialCount();

        // Restore on saved instances
        if (savedInstanceState != null) {
            qty = savedInstanceState.getInt(Constants.COUNT, 0);
            // Update the UI
            b.qty.setText(String.valueOf(qty));
        }
    }

    /**
     *Get the data from the starter activity
     */
    private void getInitialCount(){
        //Get data from intent
        Bundle bundle = getIntent().getExtras();

        if (bundle == null)
            return;

        // Getting all the data which is come from the starter activity
       qty = bundle.getInt(Constants.INITIAL_COUNT_KEY, 0);
       minVal = bundle.getInt(Constants.MINIMUM_VALUE, Integer.MIN_VALUE);
       maxVal = bundle.getInt(Constants.MAXIMUM_VALUE, Integer.MAX_VALUE);

       if(qty != 0){
           b.sendBackBtn.setVisibility(View.VISIBLE);
       }

        b.qty.setText(String.valueOf(qty));

    }

    /**
     *Trigger Event handlers to listen the actions
     */
    private void setupEventHandlers() {
        b.incBtn.setOnClickListener(v -> {
            //Calling increase function
            incQty();
        });
        b.decBtn.setOnClickListener(v -> {
            //Calling decrease function
            decQty();
        });
    }

    /**
     *To decrease the quantity and update the result in text view
     */
    public void decQty() {
        b.qty.setText("" + --qty);
    }

    /**
     *To increase the quantity and update the result in text view
     */
    public void incQty() {
        b.qty.setText("" + ++qty);
    }

    /**
     *To send the final count back to main activity
     *@param view button view which is triggered
     */
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
    /**
     * Save data
     * @param outState Save data on configuration changes
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.COUNT, qty);
    }

}
