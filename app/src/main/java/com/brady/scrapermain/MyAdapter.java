package com.brady.scrapermain;

/**
 * Created by ryani on 3/6/2017.
 */

import java.util.ArrayList;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    String response;
    String[] titles = new String[8];
    MyAdapter(String responseMsg){
        response = responseMsg;
        System.out.println("THIS IS THE RESPONSE" + response);
    }

    private int[] images = {R.drawable.creeper,
            R.drawable.creeper,
            R.drawable.creeper,
            R.drawable.creeper,
            R.drawable.creeper,
            R.drawable.creeper,
            R.drawable.creeper,
            R.drawable.creeper};


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView itemTitle;
        public TextView itemDetail;
        public int currentItem;
        public ImageView itemImage;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemDetail = (TextView) itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {

                int position = getAdapterPosition();

                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.display_layout, viewGroup, false);
        MyAdapter.ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i) {

        String[] titles = { response,
                "Chapter Two",
                "Chapter Three",
                "Chapter Four",
                "Chapter Five",
                "Chapter Six",
                "Chapter Seven",
                "Chapter Eight"};

        String[] details = {"Item One",
                "Item Two",
                "Item Three",
                "Item Four",
                "Item Five",
                "Item Six",
                "Item Seven",
                "Item Eight"};

        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}

