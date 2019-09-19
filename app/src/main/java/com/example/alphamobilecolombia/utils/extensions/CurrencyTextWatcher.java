package com.example.alphamobilecolombia.utils.extensions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.widget.EditText;

import com.example.alphamobilecolombia.utils.crashlytics.LogError;

public class CurrencyTextWatcher implements TextWatcher {

    private final static String DS = "."; //Decimal Separator
    private final static String TS = ","; //Thousands Separator
    private final static String NUMBERS = "0123456789"; //Numbers
    private final static int MAX_LENGTH = 13; //Maximum Length

    private String format;

    private DecimalFormat decimalFormat;
    private EditText editText;

    public CurrencyTextWatcher(EditText editText) {
        String pattern = "###" + TS + "###" + DS + "##";
        decimalFormat = new DecimalFormat(pattern);
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.editText.setKeyListener(DigitsKeyListener.getInstance(NUMBERS + DS));
        this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        editText.removeTextChangedListener(this);
        String value = editable.toString();
        if (!value.isEmpty()) {
            value = value.replace(TS, "");
            try {
                format = decimalFormat.format(Double.parseDouble(value));
                format = format.replace("0", "");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            editText.setText(format);
        }

        editText.addTextChangedListener(this);
    }
}