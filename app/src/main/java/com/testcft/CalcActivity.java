package com.testcft;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class CalcActivity extends AppCompatActivity {

    private TextView mTextViewHeader;
    private TextView mTextViewValute;
    private EditText mEditTextNumberDecimalRUB;
    private EditText mEditTextNumberDecimalVALUTE;

    private Valute mValuteObject;



    private final String Digits     = "(\\p{Digit}+)";
    private final String HexDigits  = "(\\p{XDigit}+)";
    private final String Exp        = "[eE][+-]?"+Digits;
    private final String fpRegex    = ("[\\x00-\\x20]*[+-]?(NaN|Infinity|((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|" +
            "(\\.("+Digits+")("+Exp+")?)|(((0[xX]" + HexDigits + "(\\.)?)|(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +
            ")[pP][+-]?" + Digits + "))[fFdD]?))[\\x00-\\x20]*");



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        mTextViewHeader = findViewById(R.id.mTextViewHeader);;
        mTextViewValute = findViewById(R.id.mTextViewValute);;
        mEditTextNumberDecimalRUB = findViewById(R.id.mEditTextNumberDecimalRUB);;
        mEditTextNumberDecimalVALUTE = findViewById(R.id.mEditTextNumberDecimalVALUTE);;

        mValuteObject = (Valute) getIntent().getExtras().getParcelable(Valute.class.getCanonicalName());

        double valute = mValuteObject.getNominal() / mValuteObject.getValue();
        ;
        mTextViewHeader.setText(String.format(getString(R.string.textViewCalcRuR), valute,mValuteObject.getCharCode()));
        mTextViewValute.setText(String.format(getString(R.string.textViewCalcValute), mValuteObject.getCharCode()));
        // mTextViewValute.setText(mValuteObject.getCharCode()+":");

        mEditTextNumberDecimalRUB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mEditTextNumberDecimalRUB.hasFocus())
                {
                    if (Pattern.matches(fpRegex, s.toString())) {
                        double temp = mValuteObject.getNominal() * Double.valueOf(s.toString()) / mValuteObject.getValue();
                        mEditTextNumberDecimalVALUTE.setText(String.format("%.4f", temp).replaceAll(",","."));
                    } else {
                        mEditTextNumberDecimalVALUTE.setText("");
                    }
                }
            }
        });


        mEditTextNumberDecimalVALUTE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mEditTextNumberDecimalVALUTE.hasFocus())
                {
                    if (Pattern.matches(fpRegex, s.toString())) {
                        double temp = mValuteObject.getValue() * Double.valueOf(s.toString()) / mValuteObject.getNominal();
                        mEditTextNumberDecimalRUB.setText(String.format("%.4f", temp).replaceAll(",","."));
                    } else {
                        mEditTextNumberDecimalRUB.setText("");
                    }
                }
            }
        });
    }


}