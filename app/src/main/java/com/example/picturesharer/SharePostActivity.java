package com.example.picturesharer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SharePostActivity extends AppCompatActivity {

    private ImageView photoImageView;
    private EditText locationEditText, descriptionEditText;
    private Button submitButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_post);

        photoImageView = findViewById(R.id.photoImageView);
        locationEditText = findViewById(R.id.locationEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        submitButton = findViewById(R.id.submitButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
            }
        });
    }

    private void savePost() {
        String location = locationEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String photoUrl = "URL_TO_PHOTO"; // Replace with actual photo URL logic

        if (location.isEmpty() || description.isEmpty() || photoUrl.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String postId = databaseReference.push().getKey();
        Post post = new Post(postId, photoUrl, location, description);
        databaseReference.child(postId).setValue(post);

        Toast.makeText(this, "Post submitted", Toast.LENGTH_SHORT).show();
        finish();
    }
}