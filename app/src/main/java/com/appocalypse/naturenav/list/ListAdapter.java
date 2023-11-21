package com.appocalypse.naturenav.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.api.POI;


import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<POI> items = new ArrayList<>();

    public void setItems(ArrayList<POI> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    protected POI getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        POI item = getItem(position);
        holder.title.setText(item.tags.get("amenity"));
        holder.subtitle.setText(item.tags.get("description"));
        holder.imageView.setImageBitmap(item.mThumbnail);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, subtitle;
        private final ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ListAdapter", "Element " + getAdapterPosition() + " clicked.");
                }
            });
            title = v.findViewById(R.id.list_item_title);
            subtitle = v.findViewById(R.id.list_item_subtitle);
            imageView = v.findViewById(R.id.list_item_image);
        }
    }
}
