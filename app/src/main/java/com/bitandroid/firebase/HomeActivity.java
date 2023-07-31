package com.bitandroid.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitandroid.firebase.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        setContentView(binding.getRoot());

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        @NonNull
        String uuid = firebaseAuth.getCurrentUser().getUid();

        firebaseDatabase.getReference()
                .child("Users")
                .child(uuid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.pbLoading.setVisibility(View.VISIBLE);
                        UserData data = snapshot.getValue(UserData.class);
                        if(data != null) {
                            binding.tvName.setText(data.name);
                            binding.tvEmail.setText(data.email);
                            binding.tvCity.setText(data.city);
                            binding.tvMobile.setText(data.mobileNo);
                        }
                        binding.pbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        
                    }
                });

    }
}