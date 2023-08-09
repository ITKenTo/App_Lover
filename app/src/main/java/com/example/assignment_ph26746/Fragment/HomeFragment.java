package com.example.assignment_ph26746.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.assignment_ph26746.Adapter.LoverAdapter;
import com.example.assignment_ph26746.Adapter.TypeAdapter;
import com.example.assignment_ph26746.Activity.AddLoverActivity;
import com.example.assignment_ph26746.Activity.DetailLoverActivity;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.Interface.IclickListener;
import com.example.assignment_ph26746.Model.Accountmodel;
import com.example.assignment_ph26746.Model.LoverModel;
import com.example.assignment_ph26746.Model.TypeModel;
import com.example.assignment_ph26746.Activity.PersionActivity;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements IclickListener {

    FragmentHomeBinding binding;
    ApiService apiService;
    List<TypeModel> listType;
    List<LoverModel> listLover;
    TypeAdapter adapter;
    LoverAdapter adapterLover;
    String id_u;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        apiService= RetrofitHelper.getService();
        listType=new ArrayList<>();
        listLover=new ArrayList<>();
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recycleType.setLayoutManager(layoutManager);
        GridLayoutManager layoutManager1= new GridLayoutManager(getActivity(),2);
        binding.recycleLover.setLayoutManager(layoutManager1);

        SharedPreferences preferences = getActivity().getSharedPreferences("USER",MODE_PRIVATE);
        id_u=preferences.getString("IDU","");


        binding.edSearchLover.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                fillter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.addLover.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddLoverActivity.class));
            Animatoo.INSTANCE.animateInAndOut(getContext());
        });


        binding.circleImageView.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PersionActivity.class));
            Animatoo.INSTANCE.animateInAndOut(getContext());
        });

        getLover();
        getType();
        getUser();
        return binding.getRoot();
    }

    private void getUser(){
        apiService.getListAcc(id_u).enqueue(new Callback<Accountmodel>() {
            @Override
            public void onResponse(Call<Accountmodel> call, Response<Accountmodel> response) {
                Accountmodel accountmodel= response.body();
                binding.textView4.setText(accountmodel.getFullname());
                String url= RetrofitHelper.url_image+accountmodel.getImage();
                Glide.with(getActivity()).load(url).into(binding.circleImageView);
                binding.animationView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Accountmodel> call, Throwable t) {

            }
        });
    }
    private void getType(){
        apiService.getListType(id_u).enqueue(new Callback<List<TypeModel>>() {
            @Override
            public void onResponse(Call<List<TypeModel>> call, Response<List<TypeModel>> response) {
                if (response.isSuccessful()){
                    listType=response.body();
                    adapter= new TypeAdapter(listType,HomeFragment.this);
                    binding.recycleType.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    binding.animationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<TypeModel>> call, Throwable t) {

            }
        });
    }

    private void getLover(){
        apiService.getList(id_u).enqueue(new Callback<List<LoverModel>>() {
            @Override
            public void onResponse(Call<List<LoverModel>> call, Response<List<LoverModel>> response) {
                if (response.isSuccessful()){
                    listLover=response.body();
                  //  Log.d("ZZZ", "onResponse: "+listLover);
                    adapterLover= new LoverAdapter(getContext(),listLover,HomeFragment.this);
                    binding.recycleLover.setAdapter(adapterLover);
                    adapterLover.notifyDataSetChanged();
                    binding.animationView.setVisibility(View.GONE);
//                    if (listLover.size()==0){
//                        binding.tvTt.setVisibility(View.VISIBLE);
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<LoverModel>> call, Throwable t) {

                Log.d("TAGLOVER", "onFailure: "+t);
            }
        });
    }

    @Override
    public void OnClick(int pos) {
     Intent intent= new Intent(getContext(), DetailLoverActivity.class);
     intent.putExtra("IDL", listLover.get(pos).get_id());
     startActivity(intent);
     Animatoo.INSTANCE.animateInAndOut(getContext());

    }

    @Override
    public void OnLongClick(int pos) {

    }

    @Override
    public void OnClickType(int pos) {
       // Toast.makeText(getActivity(), ""+listType.get(pos).getName(), Toast.LENGTH_SHORT).show();
        apiService.getListLoverType(id_u,listType.get(pos).get_id()).enqueue(new Callback<List<LoverModel>>() {
            @Override
            public void onResponse(Call<List<LoverModel>> call, Response<List<LoverModel>> response) {
                if (response.isSuccessful()){

                    listLover=response.body();
                    adapterLover= new LoverAdapter(getContext(),listLover,HomeFragment.this);
                    binding.recycleLover.setAdapter(adapterLover);
                    adapterLover.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<LoverModel>> call, Throwable t) {

            }
        });
    }


    public void fillter(String text){

        ArrayList<LoverModel> list= new ArrayList<>();
        for (LoverModel lover:listLover
             ) {
          if (lover.getName().toLowerCase().contains(text.toLowerCase())){
              list.add(lover);
          }
        }
        adapterLover.fillter(list);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLover();
        getUser();
        getType();
    }
}