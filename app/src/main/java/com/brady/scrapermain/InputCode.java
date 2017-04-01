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

    public void passToDisplay(Context context, String response) {

        Intent intent = new Intent(context, DisplayCode.class);
        intent.putExtra("Response", response);
        context.startActivity(intent);
        //((Activity)context).finish();
    }


    //This string assumes user has removed HTML for us
    //Also needs user to give us an integer for data size
    void parseInfo(String test) {
    String Test = "DD23122617085012451G46+1NN175EXE00087`BCT@Fd@Fc@Fb@Fa@Fc@F`@F_@F^@F]@F]@F[@F[@Fm@Fl@Fk@Fj@Fi@Fh@Fh@Fg@Ff@Fe@Fd@Fc@T@@T@@TJj ";


    String headerOnly = Test.substring(0, 41);
    String dataOnly = Test.substring(41);

    //Display tests
        System.out.println("headerOnly: " + headerOnly);
        System.out.println("dataOnly: " + dataOnly);


    //Puts each piece of data in array
    //Length / dataBits should be count  This isn't quite right yet, due to battery level at end.
    int dataBits = 3;
    int count = dataOnly.length() / dataBits;
    String[] data = new String[count];
    int i;
    int ss = 0;
        for (i = 0; i < count; i++) {
        data[i] = dataOnly.substring(ss, ss + dataBits);
        ss+=dataBits;
    }


    //Decode the data
    int sumData = 0;
    int index = dataBits;
        for (i=0; i< count; i++){
        for (int j = 0; j <dataBits; j++) {
            char s = data[i].charAt(j);
            index--;

            int newValue = solveNum(s, index);
            sumData += newValue;
            //System.out.println("new: " + newValue);
            //System.out.println("sum: " + sumData);
        }
        System.out.println("sum: " + sumData);
        sumData = 0;
        index = dataBits;
    }

    //Print out battery level
    int length = dataOnly.length();
    String battery = dataOnly.substring(length - 2);
    char b = battery.charAt(0);
    float bat = solveNum(b, 0);
    bat = (float) (bat * .3124 + 0.311);
        System.out.println("Battery: " + bat);

}

    private static int solveNum(char s, int index) {
        //How to decode each piece of data???

        //int ascii = s.charAt(0);
        int ascii = s;
        //int newAsc = (ascii & 0x3f);

        int value = 0;
        if (index == 0){
            value = (ascii & 0x3f);
        }
        else if (index == 1) {
            value = (ascii & 0x3f);
            value *= 64;
        }
        else if (index == 2) {
            value = (ascii & 0x3f);
            value *= 4096;
        }
        else {
            value = (ascii & 0x3f);
            value *= 262144;

        }

        return value;
    }
}
