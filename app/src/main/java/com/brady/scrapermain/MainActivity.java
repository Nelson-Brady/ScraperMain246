package com.brady.scrapermain;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/***************************************************
 * MainActivity
 *
 * Entry point and driver of program.
 * Initially, main will have nothing but a New Search
 * button and an empty body.  After being called by
 * MyAdapter, it will receive a data string from
 * MyAdapter, decode it, and then display it.  The
 * user of the app may then use the data as needed,
 * hit the back arrow to decode a new string, or even
 * start a new search and start the process all over again.
 **************************************************/
public class MainActivity extends AppCompatActivity {

    String decodedString = "start";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayInfo();
    }

    public void newCode(View view) {
        Intent intent = new Intent(this, InputCode.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void displayInfo() {
        Intent intent = getIntent();
        decodedString = intent.getStringExtra("Detail");
        if (decodedString == null) {
            Log.w("String must be valid", "Null String");
            return;
        }
        Log.d("String must be valid", decodedString);
        try {
            parseInfo(decodedString);
        } catch (Error e) {
            e.printStackTrace();
            System.out.println("Unacceptable string passed to parseInfo()");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseInfo(String decodedString) {
        //Received string should look something like the following:
        //String decodedString = "DD23122617085012451G46+1NN175EXE00087`BCT@Fd@Fc@Fb@Fa@Fc@F`@F_@F^@F]@F]@F[@F[@Fm@Fl@Fk@Fj@Fi@Fh@Fh@Fg@Ff@Fe@Fd@Fc@T@@T@@TJj ";

        String headerOnly = decodedString.substring(0, 41);
        String dataOnly = decodedString.substring(41);

        //Puts each piece of data in array
        //Length / dataBits should be count  This isn't quite right yet, due to battery level at end.
        int dataBits = 3;
        int count = dataOnly.length() / dataBits;
        String[] data = new String[count];
        int i;
        int ss = 0;
        for (i = 0; i < count; i++) {
            data[i] = dataOnly.substring(ss, ss + dataBits);
            ss += dataBits;
        }


        //Decode the data
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        int sumData = 0;
        int newLine = 0;
        int index = dataBits;
        for (i = 0; i < count; i++){
            for (int j = 0; j < dataBits; j++) {
                char s = data[i].charAt(j);
                index--;

                int newValue = solveNum(s, index);
                sumData += newValue;
            }

            float fData = sumData;
            fData /= 100;
            DecimalFormat df = new DecimalFormat("#.##");
            df.format(fData);
            textView3.append("Reading: " + fData + ", ");
            newLine++;
            if (newLine == 2) {
                textView3.append("\n");
                newLine = 0;
            }
            sumData = 0;
            index = dataBits;
        }

        //Print out battery level
        int length = dataOnly.length();
        String battery = dataOnly.substring(length - 2);
        char b = battery.charAt(0);
        float bat = solveNum(b, 0);
        bat = (float) (bat * .3124 + 0.311);
        textView3.append("Battery: " + bat);
        textView3.append("\n");
    }

    private static int solveNum(char s, int index) {

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