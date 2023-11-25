package com.appocalypse.naturenav.list;

import android.location.Location;
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
import com.appocalypse.naturenav.utility.POITypes;


import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        POI item = getItem(position);
        holder.title.setText(POITypes.amenityToStringId.get(item.tags.get("amenity")));

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            final String address = item.getAddress(holder.itemView.getContext());

            // Ensure to update UI on the main thread
            holder.itemView.post(() -> {
                holder.subtitle.setText(address != null ? address : "Indirizzo non disponibile");
            });
        });

        if (location != null && item.location != null) {
            double currentLatitude = location.getLatitude();
            double currentLongitude = location.getLongitude();
            float distance = new DistanceCalculator().distance(currentLatitude, currentLongitude, item.location.getLatitude(), item.location.getLongitude());
            holder.distance.setText(String.format("%.2f", distance) + " km");
        } else {
            holder.distance.setText("Distanza non disponibile");
        }


    }
    public class DistanceCalculator {

        // Perform the calculation of the distance between two points
        public float distance(double lat1, double lon1, double lat2, double lon2) {
            Location location1 = new Location("Point A");
            location1.setLatitude(lat1);
            location1.setLongitude(lon1);

            Location location2 = new Location("Point B");
            location2.setLatitude(lat2);
            location2.setLongitude(lon2);

            // Calculate the distance in meters between the two points
            float distance = location1.distanceTo(location2);

            return distance / 1000; // Conversion from meters to kilometers
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