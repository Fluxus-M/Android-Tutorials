package com.practice.sample.location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends AppCompatActivity {
    private static final String MAP_BUNDLE_KEY = "MapBundleKey";

    private GoogleMap map;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_BUNDLE_KEY);
        }

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                LatLng seoul = new LatLng(37.56641923090, 126.9778741551);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(seoul);
                markerOptions.title("서울");

                map.addMarker(markerOptions);

                map.moveCamera(CameraUpdateFactory.newLatLng(seoul));
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapBundle = outState.getBundle(MAP_BUNDLE_KEY);

        if (mapBundle == null) {
            mapBundle = new Bundle();
            outState.putBundle(MAP_BUNDLE_KEY, mapBundle);
        }

        mapView.onSaveInstanceState(mapBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
