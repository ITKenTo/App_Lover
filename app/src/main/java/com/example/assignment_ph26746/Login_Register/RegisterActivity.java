package com.example.assignment_ph26746.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.R;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    int temp;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_register);

        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService= RetrofitHelper.getService();
        binding.tvSignin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        binding.backLog.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnSignup.setOnClickListener(v -> {
            validate();
            if (temp==0){
                SignUp();
            }else {
                temp=0;
            }
        });
    }


    private void SignUp(){
        String username= binding.edUsername.getText().toString();
        String password= binding.edPasswd.getText().toString();
        String fullname= binding.edName.getText().toString();

        apiService.Register(username,password,fullname).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(RegisterActivity.this, "Đăng Kí Thành Công", Toast.LENGTH_SHORT).show();
                binding.edUsername.setText("");
                binding.edPasswd.setText("");
                binding.edName.setText("");
                binding.edCfPasswd.setText("");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void validate(){

            if (binding.edUsername.getText().toString().isEmpty()){
            binding.edUsername.setError("Không Để Trống Username");
            temp++;
                return;
            }
            if (binding.edName.getText().toString().isEmpty()){
            binding.edName.setError("Không Để Trống Fullname");

                temp++;
                return;
            }
            if (binding.edPasswd.getText().toString().isEmpty()){
            binding.edPasswd.setError("Không Để Trống Password");
                temp++;
                return;
            }
            if (binding.edCfPasswd.getText().toString().isEmpty()){
            binding.edCfPasswd.setError("Không Để Trống Confim Password");
                temp++;
                return;
            }
            if (!binding.edCfPasswd.getText().toString().equals(binding.edPasswd.getText().toString())){
                binding.edCfPasswd.setError("Mật Khẩu Không Trùng Khớp");
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