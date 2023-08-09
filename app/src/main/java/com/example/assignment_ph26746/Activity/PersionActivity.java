package com.example.assignment_ph26746.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.Model.Accountmodel;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityPersionBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersionActivity extends AppCompatActivity {

    ActivityPersionBinding binding;
    Uri uri;
    String id_u;
    Accountmodel accountmodel;
    MultipartBody.Part imagePart;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_persion);
        binding=ActivityPersionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = getSharedPreferences("USER",MODE_PRIVATE);
        id_u=preferences.getString("IDU","");
        Log.d("IDU", "onCreate: "+id_u);

        apiService= RetrofitHelper.getService();
        binding.imageAddAvt.setOnClickListener(v -> {
            UpadateAvt();
        });
        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.INSTANCE.animateInAndOut(this);
        });
//        binding.updateAvt.setOnClickListener(v -> {
//
//        });

        binding.inforImage.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .galleryOnly()
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

        getPersion();

    }

    private void getPersion(){
        apiService.getListAcc(id_u).enqueue(new Callback<Accountmodel>() {
            @Override
            public void onResponse(Call<Accountmodel> call, Response<Accountmodel> response) {
                accountmodel= response.body();
                String url=RetrofitHelper.url_image+accountmodel.getImage();
                Glide.with(getBaseContext()).load(url).into(binding.inforImage);
                binding.tvInfoName.setText(accountmodel.getFullname());
                binding.tvInfoUsername.setText(accountmodel.getUsername());
                binding.animationView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Accountmodel> call, Throwable t) {

            }
        });
    }

    private void UpadateAvt(){
        binding.animationView.setVisibility(View.VISIBLE);
        if (uri!=null){
            File imageFile = new File(uri.getPath());
            RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestImageFile);

            apiService.updateavt(id_u,imagePart).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("TAG", "onResponse: "+response.body());
                    Toast.makeText(PersionActivity.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                    binding.animationView.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }else {
           // File imageFile = new File(uri.getPath());
//            RequestBody requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
//            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestImageFile);

            apiService.updateavt(id_u,null).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("TAG", "onResponse: "+response.body());
                    binding.animationView.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
             uri = data.getData();
                    // Use Uri object instead of File to avoid storage permissions
                    binding.inforImage.setImageURI(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}