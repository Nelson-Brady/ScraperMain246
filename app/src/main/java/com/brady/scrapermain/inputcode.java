package com.brady.scrapermain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.security.AccessController.getContext;

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
        Geos.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }
// This function validates the form when search is pressed

    void checkForm(View view) throws IOException {

        EditText Since = (EditText) findViewById(R.id.editText3);
        EditText Geos = (EditText) findViewById(R.id.editText);

        SinceTime = Since.getText().toString();
        GeosAddress = Geos.getText().toString();

        // This will call receiveData() as a thread, POST the data
        // and then receive the resulting code.
        URL url = new URL("https://eddn.usgs.gov/cgi-bin/fieldtest.pl");
        new receiveData(this, SinceTime, GeosAddress).execute(url);
    }

    public void passToDisplay(Context context, String response) {
        Intent intent = new Intent(context, DisplayCode.class);
        intent.putExtra("Response", response);
        context.startActivity(intent);
        //((Activity)context).finish();

        //parseInfo(response);
    }

    void parseInfo(String test) {
    //This string assumes user has removed HTML for us
    //Also needs user to give us an integer for data size

        //data = "DD23122608078152451G49+1NN175EXE00087`BCT@GU@GV@GU@GV@GW@GW@GW@GW@GX@GX@GX@/GX@GY@GW@GX@G[@GZ@GZ@GY@GZ@GZ@GZ@GZ@G@WR@Un@Uxj";

    String headerOnly = test.substring(0, 41);
    String dataOnly = test.substring(41);

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
        for (i=0; i<count; i++){
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
    /*
    // this function parses the header information
    void parseInfo(String data) {

        Intent intent = new Intent(this, DisplayCode.class);
        startActivity(intent);



        /*String Address = data.substring(0, 8);

        String year = data.substring(8, 10);
        year = "20" + year;
        String JulianDay = data.substring(10, 13);

        JulianDay = year + JulianDay;
        String Date = JulianToDate(JulianDay);


        //Working on decoding data
        //String Data = decodeData(data);




    }


    //this function is used by parseInfo and it converts a julian day to a readable date.
    String JulianToDate(String julian) {
        String dateStr = julian;
        Calendar cal  = new GregorianCalendar();
        cal.set(Calendar.YEAR,Integer.parseInt(dateStr.substring(0,4)));
        cal.set(Calendar.DAY_OF_YEAR,Integer.parseInt(dateStr.substring(4)));
        Date parsedDate  = cal.getTime();

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateToString = formatter.format(parsedDate);

        return dateToString;

    }


    //working on decoding data
    String decodeData(String Test) {
        //Determine how many @'s
        int count = Test.length() - Test.replace("@", "").length();

        //Split string at each @ to get each piece of data
        String[] data = new String[count];
        int i;
        for(i = 0; i <= count; i ++) {
            data = Test.split("@");
        }

        //Put the @ back in that split removes
        for(i = 1; i<= count; i++) {
            data[i] = "@" + data[i];
            System.out.println(data[i]);
        }

        //Determine how many bits for each data piece
        String dataOnly = Test.substring(41);
        int dataSize = dataOnly.length() / count;
        System.out.println(dataSize);


        //How to decode each piece of data???
        String Data = "";
        for (i = 1; i <= count; i++) {

            int value = 0;
            //Data = "";

            String[] word = data[i].split("");

            for (int j = word.length - 1; j > 0; j--) {

                int index = word.length - j;


                value += lookUp(word[j], index - 1);

            }
            Data += value + " ";


        }
        return Data;

    }

    int lookUp(String s1, int index) {

        int ascii = s1.charAt(0);
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
*/



}
