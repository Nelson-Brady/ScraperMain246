package com.brady.scrapermain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/***************************************************
 * DisplayCode
 *
 * RecyclerView display that utilizes MyAdapter.
 * Passes the response string to MyAdapter in its
 * Non-Default Constructor, then MyAdapter adapts
 * the string to the RecyclerView where it is displayed.
 **************************************************/
public class DisplayCode extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_code);

        // get the string
        Intent intent = getIntent();
        String response = intent.getStringExtra("Response");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(response, this);
        recyclerView.setAdapter(adapter);
    }
}
