package com.kia99.skyrestaurant.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.Model.ChonHinhAnhBinhLuanModel;
import com.kia99.skyrestaurant.R;

import java.util.List;

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinh> {
    Context context;
    List<ChonHinhAnhBinhLuanModel> listDuongDan;
    int resource;

    public AdapterChonHinhBinhLuan(Context context, int resource, List<ChonHinhAnhBinhLuanModel> listDuongDan) {
        this.context = context;
        this.resource = resource;
        this.listDuongDan = listDuongDan;
    }

    public class ViewHolderChonHinh extends RecyclerView.ViewHolder {
        ImageView imageView;
        CheckBox checkBox;

        public ViewHolderChonHinh(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_chonHinhBinhLuan);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_ChonHinhAnhBinhLuan);
        }
    }

    @NonNull
    @Override
    public AdapterChonHinhBinhLuan.ViewHolderChonHinh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolderChonHinh viewHolderChonHinh = new ViewHolderChonHinh(view);
        return viewHolderChonHinh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChonHinhBinhLuan.ViewHolderChonHinh holder,final int position) {
        ChonHinhAnhBinhLuanModel chonHinhAnhBinhLuanModel = listDuongDan.get(position);
        Uri uri = Uri.parse(chonHinhAnhBinhLuanModel.getDuongdan());

        holder.imageView.setImageURI(uri);
        holder.checkBox.setChecked(chonHinhAnhBinhLuanModel.isCheck());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
//                chonHinhAnhBinhLuanModel.setCheck(checkBox.isChecked());
                listDuongDan.get(position).setCheck(checkBox.isChecked());

            }
        });
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }
}
