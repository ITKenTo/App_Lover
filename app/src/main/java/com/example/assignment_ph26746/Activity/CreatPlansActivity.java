package com.example.assignment_ph26746.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityCreatPlansBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatPlansActivity extends AppCompatActivity {

    ActivityCreatPlansBinding binding;
    String id;
    ApiService apiService;
    int temp;
    String iamge;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
    String id_u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_creat_plans);
        binding=ActivityCreatPlansBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent1= getIntent();
        id=intent1.getStringExtra("IDLD");
        iamge= intent1.getStringExtra("IMAGE");
        binding.namePlans.setText(intent1.getStringExtra("NAME"));
        SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
        id_u=preferences.getString("IDU","");

        apiService= RetrofitHelper.getService();
        Glide.with(getBaseContext()).load(iamge).into(binding.imgViewPlans);

        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.INSTANCE.animateInAndOut(this);
        });
        binding.edDate.setOnClickListener(v -> {
            datePick();
        });

        binding.btnAddPlans.setOnClickListener(v -> {
            Validate();
            if (temp==0){

                AddPlans();
            }else {
                temp=0;
            }
        });
    }

    private void AddPlans(){

        String title= binding.edTitle.getText().toString();
        String date= binding.edDate.getText().toString();
        String location= binding.edLocation.getText().toString();
        String des = binding.edDes.getText().toString();


        apiService.addPlans(title,id_u,id,date,location,des).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CreatPlansActivity.this, "Đã Tạo Lịch", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    public void Validate(){
        if (binding.edTitle.getText().toString().isEmpty()){
            binding.edTitle.setError("Vui Lòng Nhập Tên");
            temp++;
            return;
        }

        if (binding.edDate.getText().toString().isEmpty()){
            binding.edDate.setError("Vui Lòng Nhập SDT");
            temp++;
            return;
        }
        if (binding.edLocation.getText().toString().isEmpty()){
            binding.edLocation.setError("Vui Lòng Chọn Ngày");
            temp++;
            return;
        }
        if (binding.edDes.getText().toString().isEmpty()){
            binding.edDes.setError("Vui Lòng Nhập Mô tả");
            temp++;
            return;
        }
    }

    private void datePick() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Format ngày theo định dạng "dd/MM/yyyy"
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                binding.edDate.setText(simpleDateFormat.format(selectedDate.getTime()));
            }
        }, 2023, 7, 19);
        dialog.show();
    }
}