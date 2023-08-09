package com.example.assignment_ph26746.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.assignment_ph26746.Activity.DetailPlansActivity;
import com.example.assignment_ph26746.Adapter.PlansAdapter;
import com.example.assignment_ph26746.Interface.ApiService;
import com.example.assignment_ph26746.Interface.IclickListener;
import com.example.assignment_ph26746.Interface.IclickPlans;
import com.example.assignment_ph26746.Model.LoverModel;
import com.example.assignment_ph26746.Model.PlansModel;
import com.example.assignment_ph26746.R;
import com.example.assignment_ph26746.RetrofitHelper;
import com.example.assignment_ph26746.databinding.FragmentDateLoverBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DateLoverFragment extends Fragment implements IclickPlans {


    FragmentDateLoverBinding binding;
    PlansAdapter adapter;
    List<PlansModel> listPlan;
    ApiService apiService;
    String id_u;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentDateLoverBinding.inflate(inflater,container,false);

        listPlan= new ArrayList<>();
         apiService= RetrofitHelper.getService();

        SharedPreferences preferences = getActivity().getSharedPreferences("USER",MODE_PRIVATE);
        id_u=preferences.getString("IDU","");

        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        binding.recyPlans.setLayoutManager(layoutManager);
        getPlans();
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

        return binding.getRoot();
    }

    private void getPlans(){
        apiService.getPlans(id_u).enqueue(new Callback<List<PlansModel>>() {
            @Override
            public void onResponse(Call<List<PlansModel>> call, Response<List<PlansModel>> response) {
                if (response.isSuccessful()){
                    listPlan=response.body();
//                    Log.d("LISS", "onResponse: "+list);
                    if (listPlan.size()==0){
                        binding.tvList.setVisibility(View.VISIBLE);
                    }else {
                        binding.tvList.setVisibility(View.GONE);
                    }
                    adapter= new PlansAdapter(listPlan,getActivity(),DateLoverFragment.this);

                    binding.recyPlans.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    binding.animationView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PlansModel>> call, Throwable t) {

                Log.e("TAG", "onFailure: "+t );
            }
        });
    }

    public void fillter(String text){

        ArrayList<PlansModel> list= new ArrayList<>();
        for (PlansModel plansModel:listPlan
        ) {
            if (plansModel.getTitle().toLowerCase().contains(text.toLowerCase()) || plansModel.getDate_play().toLowerCase().contains(text.toLowerCase())){
                list.add(plansModel);
            }
        }
        adapter.fillter(list);
    }
    @Override
    public void OnClickPlans(int pos) {
        Intent intent= new Intent(getActivity(), DetailPlansActivity.class);
        intent.putExtra("IDP", listPlan.get(pos).get_id());
        startActivity(intent);
        Animatoo.INSTANCE.animateInAndOut(getActivity());

    }

    @Override
    public void OnLongClikPlans(int pos) {
      //  Toast.makeText(getActivity(), ""+pos, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông Báo..!");
        builder.setMessage("Bạn có chắc chắn muốn xóa cuộc hẹn này ?");
        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                apiService.deletePlans(listPlan.get(pos).get_id()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getActivity(), "Đã Xóa Lịch", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });

        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog= builder.create();
        dialog.show();


    }

}