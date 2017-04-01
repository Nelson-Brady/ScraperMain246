package com.brady.scrapermain;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.URL;

/*************************************************
 * InputCode
 *
 * Activity created by MainActivity.
 * Prompts the user for two inputs: SinceTime and
 * GoesAddress.  These variables will be used to
 * obtain the wanted data via POST request.
 ************************************************/
public class InputCode extends AppCompatActivity {

    private String SinceTime;
    private String GoesAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputcode);
        setTitle("New Input");

        // create object editText
        EditText Goes = (EditText) findViewById(R.id.editText);

        //This function insures that all characters are uppercase
        Goes.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    // This function validates the form when search is pressed
    // and starts the AsyncActivity ReceiveData to POST and receive
    // the wanted data
    void checkForm(View view) throws IOException {

        EditText Since = (EditText) findViewById(R.id.editText3);
        EditText Goes = (EditText) findViewById(R.id.editText);

        SinceTime = Since.getText().toString();
        GoesAddress = Goes.getText().toString();

        // This will call ReceiveData() as a thread, POST the data
        // and then receive the resulting code.
        URL url = new URL("https://eddn.usgs.gov/cgi-bin/fieldtest.pl");
        new ReceiveData(this, SinceTime, GoesAddress).execute(url);
    }

    // Creates the Display Code activity along with the response
    // string from ReceiveData
    public void passToDisplay(Context context, String response) {

        Intent intent = new Intent(context, DisplayCode.class);
        // pass the string
        intent.putExtra("Response", response);
        context.startActivity(intent);
    }
}
