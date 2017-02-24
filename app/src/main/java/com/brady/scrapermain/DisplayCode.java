package com.brady.scrapermain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_code);

        Intent intent = getIntent();

        String since = intent.getStringExtra("Since");

        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText(since);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_code);
        layout.addView(textView);
    }
}
