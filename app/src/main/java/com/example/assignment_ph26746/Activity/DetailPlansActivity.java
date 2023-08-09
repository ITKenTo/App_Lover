package com.example.assignment_ph26746.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.Model.PlansModel;
import com.example.assignment_ph26746.R;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityDetailPlansBinding;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPlansActivity extends AppCompatActivity {

    ActivityDetailPlansBinding binding;
    String idP;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_plans);
        binding= ActivityDetailPlansBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService= RetrofitHelper.getService();
        Intent intent= getIntent();
        idP= intent.getStringExtra("IDP");

        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.INSTANCE.animateInAndOut(this);
        });
        String locationName = binding.tvLocationDetail.getText().toString(); // Tên địa điểm

        binding.btnGgmap.setOnClickListener(v -> {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(locationName, 1);
                if (!addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    double latitude = address.getLatitude();
                    double longitude = address.getLongitude();

                    String uri = "geo:" + latitude + "," + longitude;
                    Log.d(TAG, "onCreate: "+uri);
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps"); // Chỉ định ứng dụng Google Maps

                    startActivity(intent2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

//        binding.btnGgmap.setOnClickListener(v -> {
//            Uri uri= Uri.parse("https://www.google.com/maps/@9.779349,105.6189045,11z?hl=vi-VN");
//            Intent intent1= new Intent(Intent.ACTION_VIEW,uri);
//            startActivity(intent1);
//        });



        getDetail();

    }
    private void getDetail(){
        apiService.getdetailPlans(idP).enqueue(new Callback<PlansModel>() {
            @Override
            public void onResponse(Call<PlansModel> call, Response<PlansModel> response) {
                if (response.isSuccessful()){
                    PlansModel plansModel= response.body();
                    String img= RetrofitHelper.url_image+plansModel.getId_lover().getImage();
                    Glide.with(getApplication()).load(img).into(binding.imgViewPlans);
                    binding.tvTitleDetail.setText(plansModel.getId_lover().getName());
                    binding.tvDateDetail.setText(plansModel.getDate_play());
                    binding.tvNoteDetail.setText(plansModel.getDes());
                    binding.tvLocationDetail.setText(plansModel.getLocation());

                }
            }

            @Override
            public void onFailure(Call<PlansModel> call, Throwable t) {

            }
        });
    }
}