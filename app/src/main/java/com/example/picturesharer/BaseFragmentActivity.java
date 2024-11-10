package com.example.picturesharer;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_share) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (itemId == R.id.navigation_explore) {
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_discover) {
                // Handle discover action
                return true;
            }
            return false;
        });
    }
}