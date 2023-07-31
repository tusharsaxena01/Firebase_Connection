package com.bitandroid.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bitandroid.firebase.databinding.ActivityRegisterBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();

            binding.ivDp.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);

        binding.ivDp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(RegisterActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                String name = binding.etName.getText().toString();
                String mobileNo = binding.etNumber.getText().toString();
                String city = binding.etCity.getText().toString();

                binding.pbLoading.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(
                        email,
                        password
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        binding.pbLoading.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            saveUser(email, password, name, mobileNo, city);
                            startActivity(loginIntent);
                        }else{
                            Toast.makeText(RegisterActivity.this, "User Not Created", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            binding.etEmail.setError(task.getException().getMessage().toString());
                        }
                    }
                });
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginIntent);
            }
        });

    }

    private void saveUser(String email, String password, String name, String mobileNo, String city) {
        String uuid = firebaseAuth.getCurrentUser().getUid();

        UserData data = new UserData(uuid, name, mobileNo, email, city, password);

        binding.pbLoading.setVisibility(View.VISIBLE);
        firebaseDatabase.getReference()
                .child("Users")
                .child(uuid)
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            binding.pbLoading.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "User Saved Successfully", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(RegisterActivity.this, "User Not Saved "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}