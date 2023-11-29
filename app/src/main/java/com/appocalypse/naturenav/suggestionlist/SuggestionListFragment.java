package com.appocalypse.naturenav.suggestionlist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appocalypse.naturenav.databinding.FragmentSuggestionListBinding;
import com.appocalypse.naturenav.utility.POITypes;

import java.util.ArrayList;
import java.util.List;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

public class SuggestionListFragment extends Fragment {
    public static final String TAG = "SuggestionListFragment";

    private SuggestionListViewModel viewModel;
    private FragmentSuggestionListBinding binding;

    private final SuggestionListAdapter suggestionListAdapter = new SuggestionListAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(SuggestionListViewModel.class);

        binding = FragmentSuggestionListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Log.i(TAG, "onCreateView: " + getViewLifecycleOwner().toString());
        Log.i(TAG, "onCreateView: " + getViewLifecycleOwner().getLifecycle().getCurrentState());

        viewModel.getSearchStringLiveData().observe(getViewLifecycleOwner(), searchString -> {
            Log.i(TAG, "onCreateView: searchString updated " + searchString);
            ArrayList<String> matchingStrings = new ArrayList<>();
            List<ExtractedResult> searchResult = FuzzySearch.extractSorted(searchString, POITypes.getAmenityStrings(getContext()), 65);
            for(ExtractedResult item : searchResult){
                matchingStrings.add(item.getString());
            }
            suggestionListAdapter.setItems(matchingStrings);
        });

        suggestionListAdapter.setOnSuggestionClick(viewModel.getOnSuggestionClick());
        binding.suggestionListRecyclerView.setAdapter(suggestionListAdapter);
        binding.suggestionListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return root;
    }
}