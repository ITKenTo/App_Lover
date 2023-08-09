package com.example.assignment_ph26746.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment_ph26746.Interface.IclickListener;
import com.example.assignment_ph26746.Interface.IclickPlans;
import com.example.assignment_ph26746.Model.LoverModel;
import com.example.assignment_ph26746.Model.PlansModel;
import com.example.assignment_ph26746.R;
import com.example.assignment_ph26746.RetrofitHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.ViewHodel>{


    List<PlansModel> list;
    Context context;
    IclickPlans iclickPlans;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PlansAdapter(List<PlansModel> list, Context context, IclickPlans iclickPlans ) {
        this.list = list;
        this.context=context;
        this.iclickPlans=iclickPlans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plans,parent,false);
        return new ViewHodel(view,iclickPlans);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {


        PlansModel plansModel= list.get(position);
        String url= RetrofitHelper.url_image+plansModel.getId_lover().getImage();
        Glide.with(context).load(url).into(holder.imageView);
        holder.tv_title.setText(plansModel.getTitle());
        holder.tv_date.setText(plansModel.getDate_play());
        Date date = new Date();
        String datecurren= dateFormat.format(date);
        Date date1,date2;
        try {
             date1 = dateFormat.parse(datecurren);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        try {
             date2 = dateFormat.parse(plansModel.getDate_play());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        int result = date1.compareTo(date2);

        if (result>0){
           holder.tv_stt.setVisibility(View.VISIBLE);
        }
        holder.tv_location.setText(plansModel.getLocation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void fillter(ArrayList<PlansModel> filter){
        list=filter;
        notifyDataSetChanged();
    }
    public static class ViewHodel extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView tv_title, tv_date, tv_location;
        TextView tv_stt;
        public ViewHodel(@NonNull View itemView, IclickPlans iclickPlans) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_lover);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_location=itemView.findViewById(R.id.tv_location);
            tv_stt= itemView.findViewById(R.id.tv_stt);

            itemView.setOnClickListener(v -> {
                int pos= getAdapterPosition();
                if (pos!= RecyclerView.NO_POSITION){
                    iclickPlans.OnClickPlans(pos);
                }
            });
            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    iclickPlans.OnLongClikPlans(pos);
                }
                return true;
            });
        }
    }
}
