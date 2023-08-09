package com.example.assignment_ph26746.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.assignment_ph26746.Adapter.SpinerTypeAdapter;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.Model.TypeModel;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityAddLoverBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLoverActivity extends AppCompatActivity {

    ActivityAddLoverBinding binding;
    ApiService apiService;
    List<TypeModel> listType;
    SpinerTypeAdapter adapter;
    String regex= "^[0-9]{10}$";
    Uri uri;
    String id_type,id_u;
    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_lover);
        binding=ActivityAddLoverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
        id_u=preferences.getString("IDU","");
        apiService= RetrofitHelper.getService();
        listType=new ArrayList<>();
        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.INSTANCE.animateInAndOut(this);
        });

        binding.addTypeScreen.setOnClickListener(v -> {
            startActivity(new Intent(this, AddTypeActivity.class));
            Animatoo.INSTANCE.animateInAndOut(this);
        });

        binding.imgView.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .galleryOnly()
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        binding.spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_type=listType.get(position).get_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.edDateLover.setOnClickListener(v -> {
            datePick();
        });

        binding.btnAddLover.setOnClickListener(v -> {
            Validate();
            if (temp==0){
                Addlover();
            }else {
                temp=0;
            }

        });


        getType();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            binding.imgView.setImageURI(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
    public void reset(){
        binding.edNameLover.setText("");
        binding.edDateLover.setText("");
        binding.edPhoneLover.setText("");
        binding.edDesLover.setText("");
    }

    public void Validate(){
        if (binding.edNameLover.getText().toString().isEmpty()){
            binding.edNameLover.setError("Vui Lòng Nhập Tên");
            temp++;
            return;
        }

        if (binding.edPhoneLover.getText().toString().isEmpty()){
            binding.edPhoneLover.setError("Vui Lòng Nhập SDT");
            temp++;
            return;
        }
        if (!binding.edPhoneLover.getText().toString().matches(regex)){
            binding.edPhoneLover.setError("SDT Không Đúng Định Dạng");
            temp++;
            return;
        }
        if (binding.edDateLover.getText().toString().isEmpty()){
            binding.edDateLover.setError("Vui Lòng Chọn Ngày");
            temp++;
            return;
        }
        if (binding.edDesLover.getText().toString().isEmpty()){
            binding.edDesLover.setError("Vui Lòng Nhập Mô tả");
            temp++;
            return;
        }
        if (uri==null){
            Toast.makeText(this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
            temp++;
            return;
        }
    }
    private void Addlover(){
        String name= binding.edNameLover.getText().toString();
        String phone= binding.edPhoneLover.getText().toString();
        String date= binding.edDateLover.getText().toString();
        String des= binding.edDesLover.getText().toString();
        File imageFile = new File(uri.getPath());
        RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody requestPhone = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody requestdate = RequestBody.create(MediaType.parse("multipart/form-data"), date);
        RequestBody requestdes = RequestBody.create(MediaType.parse("multipart/form-data"), des);
        RequestBody requestId = RequestBody.create(MediaType.parse("multipart/form-data"), id_type);
        RequestBody requestId_user = RequestBody.create(MediaType.parse("multipart/form-data"), id_u);
        RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestImageFile);

        apiService.addObjImage(requestName,requestPhone,requestdate,requestId,requestId_user,requestdes,imagePart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    reset();
                    Toast.makeText(AddLoverActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void getType(){
        apiService.getListType(id_u).enqueue(new Callback<List<TypeModel>>() {
            @Override
            public void onResponse(Call<List<TypeModel>> call, Response<List<TypeModel>> response) {
                if (response.isSuccessful()){
                    listType=response.body();
                    adapter= new SpinerTypeAdapter(getBaseContext(),listType);
                    binding.spnType.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<TypeModel>> call, Throwable t) {

            }
        });
    }


    private void datePick() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                binding.edDateLover.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, 2023, 7, 19);
        dialog.show();
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

    @Override
    protected void onResume() {
        super.onResume();
        getType();
    }
}