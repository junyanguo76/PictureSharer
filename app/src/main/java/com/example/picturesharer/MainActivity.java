package com.example.picturesharer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, findViewById(R.id.container));

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
    }

    private void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connect or Register to continue")
                .setItems(new String[]{"Connect", "Register"}, (dialog, which) -> {
                    if (which == 0) {
                        // Connect
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                    } else if (which == 1) {
                        // Register
                        Intent intent = new Intent(MainActivity.this, Register.class);
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }
}