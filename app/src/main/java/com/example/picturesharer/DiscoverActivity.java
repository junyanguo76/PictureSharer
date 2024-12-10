package com.example.picturesharer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiscoverActivity extends BaseActivity {

    private LinearLayout postsContainer;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_discover, findViewById(R.id.container));

        postsContainer = findViewById(R.id.postsContainer);
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        loadPosts();
    }

    @Override
    protected void setSelectedNavigationItem() {
        setBottomNavigationItem(R.id.navigation_discover);
    }

    private void loadPosts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postsContainer.removeAllViews();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    if (post != null) {
                        addPostToLayout(post);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // nothing
            }
        });
    }

    private void addPostToLayout(Post post) {
        View postView = LayoutInflater.from(this).inflate(R.layout.post_item, postsContainer, false);

        ImageView photoImageView = postView.findViewById(R.id.photoImageView);
        TextView titleTextView = postView.findViewById(R.id.titleTextView);
        TextView authorTextView = postView.findViewById(R.id.authorTextView);
        TextView descriptionTextView = postView.findViewById(R.id.descriptionTextView);

        Glide.with(this).load(post.getPhotoUrl()).into(photoImageView);
        titleTextView.setText(post.getTitle());
        authorTextView.setText(post.getAuthor());
        descriptionTextView.setText(post.getDescription());

        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullScreenImageDialog(post.getPhotoUrl());
            }
        });

        postsContainer.addView(postView);
    }

    private void showFullScreenImageDialog(String imageUrl) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_full_screen_image);

        ImageView fullScreenImageView = dialog.findViewById(R.id.fullScreenImageView);
        Button closeButton = dialog.findViewById(R.id.closeButton);

        Glide.with(this).load(imageUrl).into(fullScreenImageView);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}