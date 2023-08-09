package com.example.assignment_ph26746.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.Model.LoverModel;
import com.example.assignment_ph26746.R;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.ActivityDetailLoverBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLoverActivity extends AppCompatActivity {

    ActivityDetailLoverBinding binding;
    ApiService apiService;
    String id,id_type;
    String image,name;
    LoverModel loverModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_lover);
        binding=ActivityDetailLoverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent1= getIntent();
        id=intent1.getStringExtra("IDL");
        loverModel=new LoverModel();
        apiService= RetrofitHelper.getService();
        binding.btnAddPlansDetail.setOnClickListener(v -> {
            Intent intent= new Intent(this, CreatPlansActivity.class);
            intent.putExtra("IDLD",id );
            intent.putExtra("IMAGE", image);
            intent.putExtra("NAME", name);
            startActivity(intent);
            Animatoo.INSTANCE.animateInAndOut(this);
        });
        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.INSTANCE.animateInAndOut(this);
        });

        binding.imgMenu.setOnClickListener(v -> {
            PopupMenu popupMenu=new PopupMenu(this,binding.imgMenu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                 if (R.id.edit==item.getItemId()){
                     Intent intent= new Intent(this, UpdateLoverActivity.class);
                     intent.putExtra("IDL",id);
                     startActivity(intent);
                     return true;
                 }else
                 if (R.id.delete==item.getItemId()){
                     delete();
                     return true;
                 }
                 return false;
            });
            popupMenu.show();
        });
        getDetail();

    }

    private void delete(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo !!!");
        builder.setMessage("Bạn có chắc chắn muốn xóa "+name+" không ?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                apiService.deleteLover(id).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(DetailLoverActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                            deleteType();
                            onBackPressed();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("ERR", "onFailure: "+t );
                    }
                });
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
    }

    private void deleteType(){

        apiService.deleteType(id_type).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(DetailLoverActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    private void getDetail(){
        apiService.getDetail(id).enqueue(new Callback<LoverModel>() {
            @Override
            public void onResponse(Call<LoverModel> call, Response<LoverModel> response) {
                if (response.isSuccessful()){
                    loverModel= response.body();
                    Log.d("DDDD", "onResponse: "+loverModel);

                    String url=RetrofitHelper.url_image+loverModel.getImage();
                    binding.tvNameDetail.setText(loverModel.getName());
                    binding.tvDesDetail.setText(loverModel.getDes());
                    binding.tvPhoneDetail.setText(loverModel.getPhone());
                    binding.tvTypeDetail.setText(loverModel.getId_type().getName());
                    binding.tvYearsoldDetail.setText(loverModel.getDate());
                    image=url;
                    name=loverModel.getName();
                    id_type=loverModel.getId_type().get_id();
                    Glide.with(getApplicationContext()).load(url).into(binding.imageAvtDetail);
                }
            }

            @Override
            public void onFailure(Call<LoverModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetail();
    }
}