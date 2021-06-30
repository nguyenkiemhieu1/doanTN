package com.kia99.skyrestaurant.Adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kia99.skyrestaurant.R;

import java.util.List;

public class AdapterHinhBinhLuanDuocChon extends RecyclerView.Adapter<AdapterHinhBinhLuanDuocChon.ViewHolderHinh> {

    Context context;
    int resource;
    List<String> list;

    public AdapterHinhBinhLuanDuocChon(Context context, int resource, List<String> list) {
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    public class ViewHolderHinh extends RecyclerView.ViewHolder {
        ImageView imageView, imgXoa;
        public ViewHolderHinh(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_chonHinhBinhLuan);
            imgXoa = (ImageView) itemView.findViewById(R.id.img_XoaHinhAnhBinhLuan);
        }
    }
    @NonNull
    @Override
    public ViewHolderHinh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolderHinh viewHolderHinh = new ViewHolderHinh(view);

        return viewHolderHinh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHinh holder, int position) {
        Uri  uri = Uri.parse(list.get(position));
        holder.imageView.setImageURI(uri);
        holder.imgXoa.setTag(position);
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int) v.getTag();
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
