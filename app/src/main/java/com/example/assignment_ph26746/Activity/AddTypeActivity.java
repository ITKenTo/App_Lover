package com.example.assignment_ph26746.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityAddTypeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTypeActivity extends AppCompatActivity {

    ActivityAddTypeBinding binding;
    ApiService apiService;
    int temp;
    String id_u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_type);
        binding=ActivityAddTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences =getSharedPreferences("USER",MODE_PRIVATE);
        id_u=preferences.getString("IDU","");
        apiService= RetrofitHelper.getService();
        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.INSTANCE.animateInAndOut(this);
        });

        binding.btnAddType.setOnClickListener(v -> {
            Validate();
            if (temp==0){
                AddType();
            }else {
                temp=0;
            }
        });

    }

    private void AddType(){
        String name= binding.edNameType.getText().toString();
        String des= binding.edDesType.getText().toString();

        apiService.addType(name,des,id_u).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    binding.edNameType.setText("");
                    binding.edDesType.setText("");
                    Toast.makeText(AddTypeActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Log.e("TAG", "onFailure: "+t );
            }
        });
    }

    public void Validate(){
        if (binding.edNameType.getText().toString().isEmpty()){
            binding.edNameType.setError("Không để trống tên loại...!");
            temp++;
        }

        if (binding.edDesType.getText().toString().isEmpty()){
            binding.edNameType.setError("Không để trống tên Mô Tả...!");
            temp++;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View view= getCurrentFocus();
        if (view instanceof EditText) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        return super.onTouchEvent(event);
    }
}