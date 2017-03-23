package com.brady.scrapermain;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    void checkForm(View view) throws IOException {

        EditText Since = (EditText) findViewById(R.id.editText3);
        //EditText until = (EditText) findViewById(R.id.editText2);
        EditText Geos = (EditText) findViewById(R.id.editText);
        //EditText chan = (EditText) findViewById(R.id.editText5);

        SinceTime   = Since.getText().toString();
        //untilTime   = until.getText().toString();
        GeosAddress = Geos.getText().toString();
        //channel     = chan.getText().toString();


        Intent intent = new Intent(this, DisplayCode.class);

        intent.putExtra("Since", SinceTime);
        //intent.putExtra("until", untilTime);
        intent.putExtra("geos", GeosAddress);
        //intent.putExtra("chan", channel);

        // This will call receiveData() as a thread, POST the data
        // and then receive the resulting code.
        URL url = new URL("https://eddn.usgs.gov/cgi-bin/fieldtest.pl");
        new receiveData(SinceTime, GeosAddress).execute(url);

        startActivity(intent);
        }


    // this function parses the header information
    void parseInfo(String data) {
        //String Test = "DD23122608078152451G49+1NN175EXE00087`BCT@GU@GV@GU@GV@GW@GW@GW@GW@GX@GX@GX@GX@GY@GW@GX@G[@GZ@GZ@GY@GZ@GZ@GZ@GZ@G@WR@Un@Uxj";




        String Address = data.substring(0, 8);

        String year = data.substring(8, 10);
        year = "20" + year;
        String JulianDay = data.substring(10, 13);

        JulianDay = year + JulianDay;
        String Date = JulianToDate(JulianDay);


        //Working on decoding data
        String Data = decodeData(data);


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
        for(i=0; i <=count; i++){


        }

        // *DELETE LATER* it needed a return statement so I added this for now.
        return Test;
    }



}
