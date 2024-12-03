package com.example.picturesharer;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.picturesharer.databinding.ActivityMapsBinding;

public class MapsActivity extends BaseFragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_base); // Ensure this is set to activity_base

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Limerick and move the camera
        LatLng limerick = new LatLng(52.674804, -8.584677);
        mMap.addMarker(new MarkerOptions().position(limerick).title("Shannon Riverside Walk").icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(limerick, 12.0f));
    }
}