package com.brady.scrapermain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayCode extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_code);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<String> mDataset = new ArrayList<>();
        mDataset.add("HelloTest1");
        mDataset.add("HelloTest2");
        mDataset.add("HelloTest3");
        mDataset.add("HelloTest4");
        mAdapter = new MyAdapter(mDataset);



        recyclerView.setAdapter(mAdapter);


        Intent intent = getIntent();

        String since = intent.getStringExtra("Since");

        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText(since);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_code);
        layout.addView(textView);
    }

    void displayData(String data) {
        System.out.println(data);
    }
}
