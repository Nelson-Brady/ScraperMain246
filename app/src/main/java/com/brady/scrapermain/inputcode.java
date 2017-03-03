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
    private String channel;


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
        EditText chan = (EditText) findViewById(R.id.editText5);

        SinceTime = Since.getText().toString();
        untilTime = until.getText().toString();
        GeosAddress = Geos.getText().toString();
        channel = chan.getText().toString();


        Intent intent = new Intent(this, DisplayCode.class);

        intent.putExtra("Since", SinceTime);
        intent.putExtra("until", untilTime);
        intent.putExtra("geos", GeosAddress);
        intent.putExtra("chan", channel);

        startActivity(intent);
        }

    void parseInfo() {
        String Test = "DD23122617062152451G49+1NN175EXE00087`BCT@GU@GV@GU@GV@GW@GW@GW@GW@GX@GX@GX@GX@GY@GW@GX@G[@GZ@GZ@GY@GZ@GZ@GZ@GZ@G@WR@Un@Uxj";




    }

}
