package com.example.assignment_ph26746.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.MainActivity;
import com.example.assignment_ph26746.R;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    int temp;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService= RetrofitHelper.getService();

        binding.btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        binding.tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        binding.btnLogin.setOnClickListener(v -> {
            validate();
            if (temp==0){
                Login();
            }else {
                temp=0;
            }
        });
    }
    private void Login(){
        String username= binding.edUsername.getText().toString();
        String password= binding.edPasswd.getText().toString();

        apiService.Login(username,password).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Log.d("LOG", "onResponse: "+response.body());
                    if (response.body().equalsIgnoreCase("Tài Khoản Không Tồn Tại")){
                        Toast.makeText(LoginActivity.this, "Tài Khoản Không Tồn Tại", Toast.LENGTH_SHORT).show();
                    }else if (response.body().equalsIgnoreCase("Sai mật Khẩu")) {
                        Toast.makeText(LoginActivity.this, "ai mật Khẩu", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                        SharedPreferences perferences=getSharedPreferences("USER", MODE_PRIVATE);
                        SharedPreferences.Editor ed=perferences.edit();
                        ed.putString("IDU", response.body());
                        ed.putBoolean("CHECK",true);
                        ed.apply();
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    private void validate(){

        if (binding.edUsername.getText().toString().isEmpty()){
            binding.edUsername.setError("Không Để Trống Username");
            temp++;
            return;
        }
        if (binding.edPasswd.getText().toString().isEmpty()){
            binding.edPasswd.setError("Không Để Trống Password");
            temp++;
            return;
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