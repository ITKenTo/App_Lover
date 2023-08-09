package com.example.assignment_ph26746.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.Model.Accountmodel;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityChangePassBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {

    ActivityChangePassBinding binding;
    ApiService apiService;
    String username;
    String id_u;
    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_change_pass);
        binding=ActivityChangePassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiService= RetrofitHelper.getService();

        SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
        id_u=preferences.getString("IDU","");

        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.INSTANCE.animateInAndOut(this);
        });
        getPersion();
        binding.btnChangpass.setOnClickListener(v -> {
            Validate();
            if (temp==0){
                checkPass();
            }else {
                temp=0;
            }

        });

    }

    private void checkPass(){
        binding.animationView.setVisibility(View.VISIBLE);
        String passold= binding.edPasswdOld.getText().toString();
        apiService.CheckPass(username,passold).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){

                    if (response.body().equalsIgnoreCase("Xác Nhận Mật Khẩu Đúng")){
                        binding.tvEror.setVisibility(View.INVISIBLE);
                        binding.animationView.setVisibility(View.INVISIBLE);
                        updatePass();
                    }else {
                        binding.tvEror.setText(response.body());
                        binding.tvEror.setVisibility(View.VISIBLE);
                        binding.animationView.setVisibility(View.INVISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e("ERR", "onFailure: "+t );
            }
        });
    }

    public void setEdit(){
        binding.edCfPasswd.setText("");
        binding.edPasswdNew.setText("");
        binding.edPasswdOld.setText("");
    }
    private void updatePass(){



        apiService.ChangePass(id_u,binding.edPasswdNew.getText().toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    binding.tvEror.setText(response.body());
                    binding.tvEror.setVisibility(View.INVISIBLE);
                    Toast.makeText(ChangePassActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                    setEdit();
                    binding.animationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    private void getPersion(){
        apiService.getListAcc(id_u).enqueue(new Callback<Accountmodel>() {
            @Override
            public void onResponse(Call<Accountmodel> call, Response<Accountmodel> response) {
                Accountmodel accountmodel= response.body();
                username= accountmodel.getUsername();
            }

            @Override
            public void onFailure(Call<Accountmodel> call, Throwable t) {

            }
        });
    }



    public void Validate(){
        if (binding.edPasswdOld.getText().toString().isEmpty()){
            binding.tvEror.setText("Vui lòng nhập mật khẩu cũ");
            binding.tvEror.setVisibility(View.VISIBLE);
            temp++;
            return;
        }
        if (binding.edPasswdNew.getText().toString().isEmpty()){
            binding.tvEror.setText("Vui lòng nhập mật khẩu mới");
            binding.tvEror.setVisibility(View.VISIBLE);
            temp++;
            return;
        }
        if (binding.edCfPasswd.getText().toString().isEmpty()){
            binding.tvEror.setText("Vui lòng nhập lại mật khẩu mới");
            binding.tvEror.setVisibility(View.VISIBLE);
            temp++;
            return;
        }

        if (!binding.edCfPasswd.getText().toString().equalsIgnoreCase(binding.edPasswdNew.getText().toString())){
            binding.tvEror.setText("Mật khẩu mới khng trùng khớp");
            binding.tvEror.setVisibility(View.VISIBLE);
            temp++;
            return;
        }

       binding.tvEror.setVisibility(View.INVISIBLE);

    }
}