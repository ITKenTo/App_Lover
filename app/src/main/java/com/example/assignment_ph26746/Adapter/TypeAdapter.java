package com.example.assignment_ph26746.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_ph26746.Interface.IclickListener;
import com.example.assignment_ph26746.Model.TypeModel;
import com.example.assignment_ph26746.R;

import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHoler>{
    List<TypeModel> list;
    private IclickListener iclickListener;

    public TypeAdapter(List<TypeModel> list, IclickListener iclickListener) {

        this.list = list;
        this.iclickListener=iclickListener;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_love,parent,false);
        return new ViewHoler(view,iclickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {

        TypeModel typeModel= list.get(position);
        holder.tv_name.setText(typeModel.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView tv_name;
        public ViewHoler(@NonNull View itemView, IclickListener iclickListener) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name_home);
            itemView.setOnClickListener(v -> {
                int pos= getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION) {
                    iclickListener.OnClickType(pos);
                }
            });
        }
    }
}
