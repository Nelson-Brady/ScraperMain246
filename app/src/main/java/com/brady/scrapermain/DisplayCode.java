package com.brady.scrapermain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayCode extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_code);

        Intent intent = getIntent();

        String since = intent.getStringExtra("Since");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // specify an adapter (see also next example)
        /*ArrayList<String> mDataset = new ArrayList<>();
        mDataset.add("HelloTest1");
        mDataset.add("HelloTest2");
        mDataset.add("HelloTest3");
        mDataset.add("HelloTest4");
        mAdapter = new MyAdapter(mDataset);



        recyclerView.setAdapter(mAdapter);

        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText(since);*/

        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_code);
        //layout.addView(textView);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
