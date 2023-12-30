package com.appocalypse.naturenav.poilist;

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
import com.appocalypse.naturenav.utility.UnitConverter;

import java.util.ArrayList;

public class PoiListAdapter extends RecyclerView.Adapter<PoiListAdapter.ViewHolder> {

    private ArrayList<POI> items = new ArrayList<>();
    private AdapterOnClick<POI> adapterOnClick;

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
                .inflate(R.layout.poi_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        POI item = getItem(position);
        Context context = holder.itemView.getContext();

        String amenity = item.tags.get("amenity");
        int amenityStringId = POITypes.amenityToStringId.getOrDefault(amenity, -1);

        String title;
        if(item.tags.get("name") != null){
            title = item.tags.get("name");
        } else {
            title = amenityStringId != -1 ? context.getString(amenityStringId) : amenity;
        }

        holder.title.setText(title);
        holder.subtitle.setText(item.address != null ? item.address : context.getString(R.string.address_not_available));
        holder.distance.setText(UnitConverter.formatDistance(item.airDistanceMeters));

        holder.onClick = pos -> {
            if (adapterOnClick != null) {
                adapterOnClick.onClick(getItem(pos));
            }
        };
    }

    interface AdapterOnClick<T> {
        void onClick(T item);
    }

    interface InternalOnClick {
        void onClick(int position);
    }

    public void setOnClick(AdapterOnClick<POI> adapterOnClick) {
        this.adapterOnClick = adapterOnClick;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, subtitle, distance;
        private InternalOnClick onClick;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ListAdapter", "Element " + getAdapterPosition() + " clicked.");
                    if (onClick != null) {
                        onClick.onClick(getAdapterPosition());
                    }
                }
            });
            title = v.findViewById(R.id.list_item_title);
            subtitle = v.findViewById(R.id.list_item_subtitle);
            distance = v.findViewById(R.id.list_item_distance);
        }
    }
}