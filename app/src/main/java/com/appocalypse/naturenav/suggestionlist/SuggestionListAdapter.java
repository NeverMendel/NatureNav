package com.appocalypse.naturenav.suggestionlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appocalypse.naturenav.R;

import java.util.ArrayList;

public class SuggestionListAdapter extends RecyclerView.Adapter<SuggestionListAdapter.ViewHolder> {
    private ArrayList<String> items = new ArrayList<>();

    private SuggestionListViewModel.OnSuggestionClick onSuggestionClick;

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnSuggestionClick(SuggestionListViewModel.OnSuggestionClick onSuggestionClick) {
        this.onSuggestionClick = onSuggestionClick;
    }

    protected String getItem(int position){
        return items.get(position);
    }

    @NonNull
    @Override
    public SuggestionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggestion_list_item, parent, false);

        return new SuggestionListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionListAdapter.ViewHolder holder, int position) {
        String item = getItem(position);

        holder.suggestion.setText(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView suggestion;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ListAdapter", "Element " + getAdapterPosition() + " clicked.");
                    if(onSuggestionClick != null){
                        onSuggestionClick.onClick(suggestion.getText().toString());
                    }
                }
            });
            suggestion = v.findViewById(R.id.suggestion_text_view);
        }
    }
}
