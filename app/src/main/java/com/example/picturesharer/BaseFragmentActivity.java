package com.example.picturesharer;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseFragmentActivity extends FragmentActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_share) {
                if (!this.getClass().equals(MainActivity.class)) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.navigation_explore) {
                if (!this.getClass().equals(MapsActivity.class)) {
                    startActivity(new Intent(this, MapsActivity.class));
                    finish();
                }
                return true;
            } else if (itemId == R.id.navigation_discover) {
                if (!this.getClass().equals(DiscoverActivity.class)) {
                    startActivity(new Intent(this, DiscoverActivity.class));
                    finish();
                }
                return true;
            }
            return false;
        });

        // Set the correct tab as selected when the activity is created
        setSelectedNavigationItem();
    }

    // Abstract method to be implemented by each child activity
    protected abstract void setSelectedNavigationItem();

    // Helper method to set the selected item programmatically
    protected void setBottomNavigationItem(int itemId) {
        bottomNavigationView.setSelectedItemId(itemId);
    }
}