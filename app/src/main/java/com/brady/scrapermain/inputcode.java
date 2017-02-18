package com.brady.scrapermain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;

public class inputcode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputcode);
        setTitle("New Input");
        // create object editText
        EditText editText = (EditText) findViewById(R.id.editText);
        //This function insures that all characters are uppercase
        editText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

    }
}
