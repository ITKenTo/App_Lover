package com.example.assignment_ph26746;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.assignment_ph26746.Fragment.DateLoverFragment;
import com.example.assignment_ph26746.Fragment.HomeFragment;
import com.example.assignment_ph26746.Fragment.SettingFragment;
import com.example.assignment_ph26746.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framgment, new HomeFragment()).commit();
            binding.bottomnavigation.setItemSelected(R.id.nav_home,true);
        }

        setUptabar();
    }
    private void setUptabar(){

        binding.bottomnavigation.setOnItemSelectedListener(i -> {
            Fragment fragment= null;
            if (i==R.id.nav_home) {
                fragment= new HomeFragment();
            }
//            if (i==R.id.nav_love) {
//                fragment= new HomeFragment();
//            }
            if (i==R.id.nav_datelover) {
                fragment= new DateLoverFragment();
            }
            if (i==R.id.nav_setting) {
                fragment= new SettingFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.framgment,fragment).commit();
        });
    }
}