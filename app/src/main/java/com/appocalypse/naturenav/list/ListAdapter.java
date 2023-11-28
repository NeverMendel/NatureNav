package com.appocalypse.naturenav.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.api.POI;
import com.appocalypse.naturenav.utility.POITypes;


import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<POI> items = new ArrayList<>();
    private GeoPoint location;

    public void setItems(ArrayList<POI> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setCurrentLocation(GeoPoint location) {
        this.location = location;
        notifyDataSetChanged();
    }

    protected POI getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poi_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        POI item = getItem(position);
        Context context = holder.itemView.getContext();

        String amenity = item.tags.get("amenity");
        int amenityStringId = POITypes.amenityToStringId.getOrDefault(amenity, -1);

        holder.title.setText(amenityStringId != -1 ? context.getString(amenityStringId) : amenity);
        holder.subtitle.setText(item.address != null ? item.address : context.getString(R.string.address_not_available));

        if (location != null) {
            double distanceKm = location.distanceToAsDouble(item.getGeopoint()) / 1000;
            holder.distance.setText(String.format(context.getString(R.string.distance_km_format), distanceKm));
        } else {
            holder.distance.setText("N/A");
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, subtitle, distance;

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
            distance = v.findViewById(R.id.list_item_distance);
        }
    }
}