package com.brady.scrapermain;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class inputcode extends AppCompatActivity {

    private String SinceTime;
    private String untilTime;
    private String GeosAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputcode);
        setTitle("New Input");
        // create object editText
        EditText Geos = (EditText) findViewById(R.id.editText);

        //This function insures that all characters are uppercase
        Geos.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }
// This function validates the form when search is pressed

    void checkForm(View view) {

        EditText Since = (EditText) findViewById(R.id.editText3);
        EditText until = (EditText) findViewById(R.id.editText2);
        EditText Geos = (EditText) findViewById(R.id.editText);

        SinceTime = Since.getText().toString();
        untilTime = until.getText().toString();
        GeosAddress = Geos.getText().toString();

        Intent intent = new Intent(this, DisplayCode.class);

        intent.putExtra("Since", SinceTime);
        intent.putExtra("until", untilTime);
        intent.putExtra("geos", GeosAddress);

        startActivity(intent);
        }

}
