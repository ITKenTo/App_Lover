package com.example.assignment_ph26746.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_ph26746.Model.TypeModel;
import com.example.assignment_ph26746.R;

import java.lang.reflect.Type;
import java.util.List;

public class SpinerTypeAdapter extends ArrayAdapter<TypeModel> {

    TextView tv_name;
    List<TypeModel> list;

    public SpinerTypeAdapter(@NonNull Context context, List<TypeModel> list) {
        super(context, 0,list);
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View hoder= convertView;
        if (hoder==null){
            LayoutInflater inflater= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            hoder= inflater.inflate(R.layout.item_spiner,null,false);
        }
        final TypeModel tp= list.get(position);
        if (tp!=null){
            tv_name=hoder.findViewById(R.id.tv_name_type);
            tv_name.setText(tp.getName());
        }
        return hoder;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View hoder= convertView;
        if (hoder==null){
            LayoutInflater inflater= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            hoder= inflater.inflate(R.layout.item_spiner,null,false);
        }
        final TypeModel tp= list.get(position);
        if (tp!=null){
            tv_name=hoder.findViewById(R.id.tv_name_type);
            tv_name.setText(tp.getName());
        }
        return hoder;
    }
}
