package com.example.picturesharer;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SharePostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button uploadButton;
    private EditText latitudeEditText, longitudeEditText, descriptionEditText;
    private Button submitButton;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Uri imageUri;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_post);

        uploadButton = findViewById(R.id.uploadButton);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        submitButton = findViewById(R.id.submitButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        storageReference = FirebaseStorage.getInstance().getReference("post_images");
        mAuth = FirebaseAuth.getInstance();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadButton.setText("Image Selected");
        }
    }

    private void savePost() {
        EditText titleEditText = findViewById(R.id.titleEditText);
        String title = titleEditText.getText().toString().trim();
        String latitude = latitudeEditText.getText().toString().trim();
        String longitude = longitudeEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (latitude.isEmpty() || longitude.isEmpty() || description.isEmpty() || imageUri == null || currentUser == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileExtension = getFileExtension(imageUri);
        if (fileExtension == null) {
            Toast.makeText(this, "Invalid file type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add a unique file name to prevent conflicts
        String fileName = currentUser.getUid() + "_" + System.currentTimeMillis() + "." + fileExtension;
        StorageReference fileReference = storageReference.child(fileName);

        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String photoUrl = uri.toString();
                        String userEmail = currentUser.getEmail();
                        String postId = databaseReference.push().getKey();

                        if (postId != null) {
                            Post post = new Post(postId, photoUrl, latitude, longitude, description, userEmail, title);

                            // Save the post to the database
                            databaseReference.child(postId).setValue(post)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(SharePostActivity.this, "Post submitted successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SharePostActivity.this, "Failed to save post: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(SharePostActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SharePostActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(((ContentResolver) cR).getType(uri));
    }
}