package com.example.assignment_ph26746.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment_ph26746.Interface.IclickListener;
import com.example.assignment_ph26746.Model.LoverModel;
import com.example.assignment_ph26746.R;
import com.example.assignment_ph26746.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

public class LoverAdapter extends RecyclerView.Adapter<LoverAdapter.ViewHolder>{
    private List<LoverModel> list;
    private final IclickListener iclickListener;
    Context context;

    public LoverAdapter(Context context,List<LoverModel> list, IclickListener iclickListener) {
        this.context = context;
        this.list = list;
        this.iclickListener = iclickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view,iclickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoverModel loverModel= list.get(position);

        holder.tv_name.setText(loverModel.getName());
        holder.tv_idtype.setText(loverModel.getId_type().getName());

        if (loverModel.getImage()==null) {
            holder.imageView.setVisibility(View.GONE);

        }else {
            String url= RetrofitHelper.url_image+loverModel.getImage();
            Glide.with(context).load(url).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(v -> {
            if (iclickListener!=null) {
                iclickListener.OnClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (iclickListener!=null) {
                iclickListener.OnLongClick(position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_phone, tv_des,tv_idtype,tv_date;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView, IclickListener iclickListener) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_idtype=itemView.findViewById(R.id.tv_type);
            imageView=itemView.findViewById(R.id.img_lover);
            itemView.setOnClickListener(v -> {
                int pos= getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION) {
                    iclickListener.OnClick(pos);
                }
            });

            itemView.setOnLongClickListener(v -> {
                int pos= getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION) {
                    iclickListener.OnLongClick(pos);
                }
                return true;
            });
        }
    }
    public void fillter(ArrayList<LoverModel> filter){
        list=filter;
        notifyDataSetChanged();
    }

}
