package com.example.assignment_ph26746.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.assignment_ph26746.MainActivity;
import com.example.assignment_ph26746.R;
import com.example.assignment_ph26746.databinding.ActivityLetstartBinding;

public class LetstartActivity extends AppCompatActivity {

    ActivityLetstartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letstart);
        binding= ActivityLetstartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnStart.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
        Boolean check= preferences.getBoolean("CHECK",false);

        if (check==true){
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}