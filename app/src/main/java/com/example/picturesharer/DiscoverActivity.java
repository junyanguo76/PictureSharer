package com.example.picturesharer;

import android.os.Bundle;

import androidx.cardview.widget.CardView;

public class DiscoverActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_discover, findViewById(R.id.container));

        CardView cardView1 = findViewById(R.id.cardView1);
        CardView cardView2 = findViewById(R.id.cardView2);
        CardView cardView3 = findViewById(R.id.cardView3);

        cardView1.setCardElevation(4);
        cardView2.setCardElevation(4);
        cardView3.setCardElevation(4);
    }
}
