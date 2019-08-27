package com.example.alphamobilecolombia.mvp.activity;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class EditTextCurrency extends AppCompatEditText {
    public EditTextCurrency(Context context) {
        super(context);
    }

    public EditTextCurrency(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(new CurrencyTextWatcher(this));
    }
}

class CurrencyTextWatcher implements TextWatcher {

    private final static String DS = "."; //Decimal Separator
    private final static String TS = ","; //Thousands Separator
    private final static String NUMBERS = "0123456789"; //Numbers
    private final static int MAX_LENGTH = 13; //Maximum Length

    private String format;

/*    private DecimalFormat decimalFormat;*/
    private EditText editText;

    public CurrencyTextWatcher(EditText editText) {
        /*String pattern = "###" + TS + "###" + DS + "##";*/
 /*       String pattern = "###,###,###";
        decimalFormat = new DecimalFormat(pattern);*/

        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.editText.setKeyListener(DigitsKeyListener.getInstance(NUMBERS + DS + "$"));
        /*this.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});*/
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        format = "";

        editText.removeTextChangedListener(this); //Quita es evento

        String value = editable.toString(); //Obtiene el texto del campo

        value = value.replace(DS, "");
        value = value.replace("$", "");

        //Si el valor no es nada entonces
        if (!TextUtils.isEmpty(value)) {



            try {
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String yourFormattedString = formatter.format(Long.parseLong(value));

                format = yourFormattedString;

                editText.setText("$" + format);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            editText.setText(format);
        }


        editText.setSelection(editText.getText().length());

        editText.addTextChangedListener(this);
    }
/*@Override
    public void afterTextChanged(Editable view) {
        String s = null;
        try {
            // The comma in the format specifier does the trick
            s = String.format("%,d", Long.parseLong(view.toString()));
            editText.setText(s);
        } catch (NumberFormatException e) {
        }
        // Set s back to the view after temporarily removing the text change listener
    }*/


}