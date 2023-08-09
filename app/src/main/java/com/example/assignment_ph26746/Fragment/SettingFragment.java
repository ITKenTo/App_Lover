package com.example.assignment_ph26746.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.assignment_ph26746.Activity.ChangePassActivity;
import com.example.assignment_ph26746.Login_Register.LoginActivity;
import com.example.assignment_ph26746.Activity.PersionActivity;
import com.example.assignment_ph26746.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentSettingBinding.inflate(inflater,container,false);
        binding.linerLogout.setOnClickListener(v -> {
            SharedPreferences perferences=getContext().getSharedPreferences("USER", MODE_PRIVATE);
            SharedPreferences.Editor ed=perferences.edit();
            ed.putBoolean("CHECK",false);
            ed.apply();
            startActivity(new Intent(getContext(), LoginActivity.class));
            Animatoo.INSTANCE.animateInAndOut(getContext());
        });


        binding.linerPersion.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PersionActivity.class));
            Animatoo.INSTANCE.animateInAndOut(getContext());
        });

        binding.linerPasseord.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ChangePassActivity.class));
            Animatoo.INSTANCE.animateInAndOut(getContext());
        });
        return binding.getRoot();
    }
}