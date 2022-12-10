package com.example.contractorcalculator;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.zip.Inflater;

public class SelectTaxRate extends DialogFragment {
    private EditText editTaxRate;
    private TextView TaxRate;
    private SelectRateListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rate_dialog,null);

        builder.setView(view).setTitle("Enter the new tax Rate")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String EditTax = editTaxRate.getText().toString();
                        listener.applyText(EditTax);
                        if (editTaxRate.getText().toString().equalsIgnoreCase("ChangeRatePreference")) {
                            editTaxRate.setText("5");
                        }
                        saveItem(editTaxRate.getText().toString());
                        getContext().getSharedPreferences("ChangeRatePreference",
                                        Context.MODE_PRIVATE).edit()
                                .putString("Rate",editTaxRate.getText().toString()).apply();


                    }
                });
        editTaxRate= view.findViewById(R.id.editNewRate);
        TaxRate= view.findViewById(R.id.tvRate2);
        initChangeRate();
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (SelectRateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement SelectRateListener");
        }
    }

    public interface SelectRateListener{
        void applyText(String nTaxRate);

    }

    private void saveItem(String T){
        SelectTaxRate.SelectRateListener activity=(SelectRateListener) getActivity();
        activity.applyText(T);
        dismiss();
    }
    private void initChangeRate(){
        String changeRt = getContext().getSharedPreferences("ChangeRatePreference",
                Context.MODE_PRIVATE).getString("Rate", "5");
        editTaxRate.setText(changeRt);
    }
}
