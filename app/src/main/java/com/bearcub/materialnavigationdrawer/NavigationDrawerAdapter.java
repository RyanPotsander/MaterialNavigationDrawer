package com.bearcub.materialnavigationdrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Home on 4/27/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private static final String TAG = "navigation_adapter";
    private final LayoutInflater inflater;
    List<NavigationDrawerItem> list = Collections.emptyList();
    Context context;

    public NavigationDrawerAdapter(Context context, List<NavigationDrawerItem> list){
        inflater= LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    public void deleteItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.navigation_drawer_item, viewGroup, false);

        MyViewHolder holder = new MyViewHolder(view);

        Log.d(TAG, "onCreateViewHolder called");
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindVH called");

        NavigationDrawerItem currentItem = list.get(position);
        viewHolder.label.setText(currentItem.label);
        viewHolder.image.setImageResource(currentItem.imageId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            label = (TextView)itemView.findViewById(R.id.navigation_row_text);
            image = (ImageView)itemView.findViewById(R.id.navigation_row_img);

            label.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "you click position " + getPosition() + " you silly goose", Toast.LENGTH_SHORT).show();
        }
    }
}
