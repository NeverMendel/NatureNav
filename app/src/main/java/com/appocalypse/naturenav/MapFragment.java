package com.appocalypse.naturenav;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import com.appocalypse.naturenav.MapViewModel;
import com.appocalypse.naturenav.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();

        MapView mapView = binding.osmmap;
        IMapController controller = mapView.getController();

        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getLocalVisibleRect(new Rect());
        mapView.setMinZoomLevel(4.0);
        mapView.setMaxZoomLevel(20.0);
        controller.setZoom(10.0);
        controller.setCenter(new GeoPoint(45.440845, 12.315515)); // Venice

        MyLocationNewOverlay mMyLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);

        mapView.setExpectedCenter(mMyLocationOverlay.getMyLocation());

        mMyLocationOverlay.enableMyLocation();
        mMyLocationOverlay.enableFollowLocation();
        mMyLocationOverlay.setDrawAccuracyEnabled(true);
        mMyLocationOverlay.runOnFirstFix(() -> {
            requireActivity().runOnUiThread(() -> {
                controller.setCenter(mMyLocationOverlay.getMyLocation());
                controller.animateTo(mMyLocationOverlay.getMyLocation());
            });
        });

        mapView.getOverlays().add(mMyLocationOverlay);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}