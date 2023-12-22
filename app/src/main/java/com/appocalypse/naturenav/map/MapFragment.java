package com.appocalypse.naturenav.map;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.appocalypse.naturenav.R;
import com.appocalypse.naturenav.api.POI;
import com.appocalypse.naturenav.bottomsheet.BottomSheetDialogViewModel;
import com.appocalypse.naturenav.databinding.FragmentMapBinding;
import com.appocalypse.naturenav.poiinfo.PoiInfoViewModel;
import com.appocalypse.naturenav.utility.PoiFinder;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private static final String TAG = "MapFragment";

    private FragmentMapBinding binding;
    private GeoPoint myLocation;
    private MapView mapView;

    private List<Marker> poiMarkers = new ArrayList<>();

    public static String getTAG() {
        return TAG;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMapBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();

        BottomSheetDialogViewModel bottomSheetDialogViewModel = new ViewModelProvider(requireActivity()).get(BottomSheetDialogViewModel.class);
        PoiInfoViewModel poiInfoViewModel = new ViewModelProvider(requireActivity()).get(PoiInfoViewModel.class);

        mapView = binding.osmmap;
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

        PoiFinder poiFinder = PoiFinder.getInstance();

        poiFinder.getPoisLiveData().observe(getViewLifecycleOwner(), newPois -> {
            // Remove existing markers
            for (Marker marker : poiMarkers) {
                mapView.getOverlays().remove(marker);
            }
            poiMarkers.clear(); // Clear the list of markers

            // Add new poi markers
            for (POI poi : newPois) {
                Marker marker = new Marker(mapView);
                marker.setPosition(poi.getGeoPoint());
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_poi_location_24));
                marker.setOnMarkerClickListener((marker1, mapView1) -> {
                    controller.animateTo(marker1.getPosition());
                    bottomSheetDialogViewModel.setDisplayingList(false);
                    poiInfoViewModel.setDisplayedPoi(poi);
                    return true;
                });

                mapView.getOverlays().add(marker);
                poiMarkers.add(marker);
            }

            // Refresh the map
            mapView.invalidate();
        });

        poiInfoViewModel.getDisplayedPoiLiveData().observe(getViewLifecycleOwner(), poi -> {
            if (poi != null) {
                highlightRouteToPoi(poi);
            }
        });

        bottomSheetDialogViewModel.getDisplayingListLiveData().observe(getViewLifecycleOwner(), isNotSelected -> {
            if (isNotSelected) {
                removeRouteHighlight();
            }

        });

        if (savedInstanceState != null) {
            myLocation = (GeoPoint) savedInstanceState.getSerializable("location");
            Log.i(TAG, "loaded location from savedInstanceState");
        } else {
            myLocation = mMyLocationOverlay.getMyLocation();
            Log.i(TAG, "not loaded location from savedInstanceState");
            if (myLocation != null) {
                Log.i(TAG, "location latitude: " + myLocation.getLatitude() + ", location longitude: " + myLocation.getLongitude());
            }
        }

        mapView.setExpectedCenter(myLocation);

        mMyLocationOverlay.enableMyLocation();
        mMyLocationOverlay.enableFollowLocation();
        mMyLocationOverlay.setDrawAccuracyEnabled(true);
        mMyLocationOverlay.runOnFirstFix(() -> {
            requireActivity().runOnUiThread(() -> {
                controller.setCenter(myLocation);
                controller.animateTo(myLocation);
            });
        });

        mapView.getOverlays().add(mMyLocationOverlay);

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("location", myLocation);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void highlightRouteToPoi(POI destinationPoi) {
        if (destinationPoi.road != null && destinationPoi.road.mStatus == Road.STATUS_OK) {
            // Remove existing PolylineOverlays
            mapView.getOverlays().removeIf(overlay -> overlay instanceof Polyline);

            // Extract the waypoints from the Road object
            ArrayList<GeoPoint> waypoints = new ArrayList<>();
            for (RoadNode node : destinationPoi.road.mNodes) {
                waypoints.add(node.mLocation);
            }

            // Create a PolylineOverlay
            Polyline polyline = new Polyline();
            polyline.setPoints(waypoints);
            // Change the color of the line to my_blue
            polyline.setColor(Color.parseColor("#2196F3"));

            // Add the PolylineOverlay to the map
            mapView.getOverlays().add(polyline);

            // Refresh the map
            mapView.invalidate();
        }
    }
    public void removeRouteHighlight() {
        mapView.getOverlays().removeIf(overlay -> overlay instanceof Polyline);
        mapView.invalidate();
    }
}