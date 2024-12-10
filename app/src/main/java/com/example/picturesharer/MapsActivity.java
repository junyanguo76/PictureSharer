package com.example.picturesharer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends BaseFragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
    }

    @Override
    protected void setSelectedNavigationItem() {
        setBottomNavigationItem(R.id.navigation_explore);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Load posts and add markers
        loadPostsAndAddMarkers();
    }

    private void loadPostsAndAddMarkers() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    if (post != null) {
                        double latitude = Double.parseDouble(post.getLatitude());
                        double longitude = Double.parseDouble(post.getLongitude());
                        LatLng postLocation = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions()
                                .position(postLocation)
                                .title(post.getTitle())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin)));
                    }
                }
                // Optionally, move the camera to the first post location
                if (dataSnapshot.getChildren().iterator().hasNext()) {
                    Post firstPost = dataSnapshot.getChildren().iterator().next().getValue(Post.class);
                    if (firstPost != null) {
                        LatLng firstPostLocation = new LatLng(Double.parseDouble(firstPost.getLatitude()), Double.parseDouble(firstPost.getLongitude()));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(firstPostLocation, 12.0f));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}