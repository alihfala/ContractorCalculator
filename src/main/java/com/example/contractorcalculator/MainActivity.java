package com.example.contractorcalculator;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SelectTaxRate.SelectRateListener {

    Button changeRate;
    TextView NewTaxRate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText eTLabor = findViewById(R.id.editTextLabor);
        EditText eTMaterial = findViewById(R.id.editTextMaterial);

        Button calculateBtn = findViewById(R.id.calculateButton);

        TextView tVSubTotal = findViewById(R.id.textViewSubTotal);
        TextView tVTax = findViewById(R.id.textViewTax);
        TextView tVTotal = findViewById(R.id.textViewTotal);
        NewTaxRate = findViewById(R.id.NewRate);
        changeRate = findViewById(R.id.changeRateBTN);
        changeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

        });


        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double subTotal = Double.parseDouble(eTLabor.getText().toString()) + Double.parseDouble(eTMaterial.getText().toString());
                double tax = subTotal * 5 / 100.0;
                double total = subTotal + tax;
                double Ntx = subTotal * Double.parseDouble(NewTaxRate.getText().toString())/100.0;
               double Ntotal = subTotal + Ntx;
                tVSubTotal.setText("$" + String.format("%.2f", subTotal));

               if (NewTaxRate.getText().toString()==null) {
                    tVTax.setText("$" + String.format("%.2f", tax));
                   tVTotal.setText("$" + String.format("%.2f", total));

               } else {
                   tVTax.setText("$" + String.format("%.2f", Ntx));
                   tVTotal.setText("$" + String.format("%.2f", Ntotal));

               }

              }


        });
    }
    public void openDialog(){
        SelectTaxRate selectTaxRate = new SelectTaxRate();
        selectTaxRate.show(getSupportFragmentManager(), "Select Tax Rate");
    }

    @Override
    public void applyText(String nTaxRate) {
        NewTaxRate.setText(nTaxRate.toString());
    }


}